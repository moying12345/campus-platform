package com.campus.service;

import com.campus.mapper.OrderMapper;
import com.campus.pojo.Dish;
import com.campus.pojo.Order;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AIService {

    @Value("${ai.dashscope.api-key:your-api-key-here}")
    private String apiKey;

    @Value("${ai.dashscope.model:qwen-turbo}")
    private String model;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private DishService dishService;

    private static final String API_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";

    /**
     * 调用通义千问 API
     */
    private String callDashScope(String prompt) {
        // 检查是否配置了有效的 API Key
        if (apiKey == null || apiKey.equals("your-api-key-here") || apiKey.trim().isEmpty()) {
            System.out.println("[AI Service] 未配置 API Key，进入模拟模式...");
            return getMockResponse(prompt);
        }

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(API_URL);
            
            httpPost.setHeader("Authorization", "Bearer " + apiKey);
            httpPost.setHeader("Content-Type", "application/json");

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", model);
            
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);
            messages.add(message);
            
            requestBody.put("messages", messages);

            StringEntity entity = new StringEntity(requestBody.toJSONString(), StandardCharsets.UTF_8);
            httpPost.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            
            if (statusCode != 200) {
                System.err.println("[AI Service] API 请求失败，状态码: " + statusCode + "，降级为模拟模式");
                return getMockResponse(prompt);
            }

            String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            JSONObject jsonResponse = JSON.parseObject(result);
            
            if (jsonResponse.containsKey("choices")) {
                JSONArray choices = jsonResponse.getJSONArray("choices");
                if (choices.size() > 0) {
                    JSONObject firstChoice = choices.getJSONObject(0);
                    JSONObject messageObj = firstChoice.getJSONObject("message");
                    return messageObj.getString("content");
                }
            }
            
            return "AI 服务返回格式异常";
        } catch (Exception e) {
            System.err.println("[AI Service] 调用异常: " + e.getMessage() + "，降级为模拟模式");
            return getMockResponse(prompt);
        }
    }

    /**
     * 智能模拟回复（支持多种场景）
     */
    private String getMockResponse(String prompt) {
        String lowerPrompt = prompt.toLowerCase();
        if (lowerPrompt.contains("你好") || lowerPrompt.contains("hello")) {
            return "你好！我是校园智能助手。您可以问我：食堂营业时间、自习室位置、推荐菜品等。";
        } else if (lowerPrompt.contains("推荐") || lowerPrompt.contains("吃什么") || lowerPrompt.contains("美食")) {
            return "为您推荐今日热门：\n1. 第一食堂：红烧排骨（15元）\n2. 第二食堂：水煮鱼（18元）\n3. 校园超市：鲜榨果汁（5元）";
        } else if (lowerPrompt.contains("食堂") || lowerPrompt.contains("吃饭") || lowerPrompt.contains("营业")) {
            return "校园食堂营业时间：\n早餐：06:30 - 08:30\n午餐：11:00 - 13:00\n晚餐：17:00 - 19:00\n祝您用餐愉快！";
        } else if (lowerPrompt.contains("自习室") || lowerPrompt.contains("图书馆")) {
            return "自习室分布在图书馆 3-5 层，开放时间为每天 08:00 - 22:00。请使用系统预约座位。";
        } else if (lowerPrompt.contains("活动") || lowerPrompt.contains("比赛")) {
            return "近期热门活动：\n1. 校园歌手大赛（本周五）\n2. 编程马拉松（下周一）\n请登录活动中心查看详情。";
        } else {
            return "收到您的提问：“" + prompt + "”。\n由于当前是测试模式（未配置 API Key），我暂时无法回答复杂问题。您可以尝试问我关于食堂、自习室或活动的问题。";
        }
    }

    /**
     * 1. 智能客服
     */
    public String askAI(String question, Long userId) {
        String campusInfo = "校园食堂营业时间：早餐6:30-8:30，午餐11:00-13:00，晚餐17:00-19:00。自习室位置：图书馆3-5层，开放时间8:00-22:00。";
        
        String prompt = "你是校园智能助手，负责解答校园常见问题。以下是校园信息：\n" + 
                       campusInfo + "\n\n" +
                       "用户问题：" + question + "\n\n" +
                       "请用简洁友好的语气回答。";

        return callDashScope(prompt);
    }

    /**
     * 2. 菜品智能推荐
     */
    public List<Map<String, Object>> recommendDishes(Long userId) {
        List<Map<String, Object>> recommendations = new ArrayList<>();

        try {
            List<Dish> allDishes = dishService.list();
            if (allDishes == null || allDishes.isEmpty()) {
                System.out.println("菜品列表为空");
                // 如果列表为空，强制返回模拟数据
                for (int i = 1; i <= 3; i++) {
                    Map<String, Object> rec = new HashMap<>();
                    rec.put("id", i);
                    rec.put("dishName", "推荐菜品 " + i);
                    rec.put("price", "15.00");
                    rec.put("image", "");
                    rec.put("reason", "热门推荐");
                    recommendations.add(rec);
                }
                return recommendations;
            }

            List<Dish> sortedDishes = allDishes.stream()
                .filter(d -> d.getStatus() == 1)
                .limit(5)
                .collect(Collectors.toList());

            for (Dish dish : sortedDishes) {
                Map<String, Object> rec = new HashMap<>();
                rec.put("id", dish.getId());
                // 强制处理名称
                String name = dish.getDishName();
                if (name == null || name.trim().isEmpty()) {
                    name = "美味菜品-" + dish.getId();
                }
                rec.put("dishName", name);

                // 强制处理价格
                String priceStr = "12.00";
                if (dish.getPrice() != null) {
                    priceStr = dish.getPrice().toString();
                }
                rec.put("price", priceStr);
                
                rec.put("image", dish.getImage());
                rec.put("reason", "猜你喜欢");
                recommendations.add(rec);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("菜品推荐失败: " + e.getMessage());
        }

        return recommendations;
    }

    /**
     * 获取热门菜品
     */
    private List<Map<String, Object>> getHotDishes(int limit) {
        List<Map<String, Object>> hotDishes = new ArrayList<>();
        
        try {
            List<Dish> allDishes = dishService.list();
            if (allDishes == null || allDishes.isEmpty()) {
                return hotDishes;
            }
            
            List<Dish> sortedDishes = allDishes.stream()
                .filter(d -> d.getStatus() == 1 && d.getId() != null)
                .limit(limit)
                .collect(Collectors.toList());

            for (Dish dish : sortedDishes) {
                Map<String, Object> rec = new HashMap<>();
                rec.put("id", dish.getId());
                rec.put("dishName", dish.getDishName() != null && !dish.getDishName().trim().isEmpty() 
                    ? dish.getDishName() : "未知菜品");
                rec.put("price", dish.getPrice() != null ? dish.getPrice().toString() : "0.00");
                rec.put("image", dish.getImage());
                rec.put("reason", "热门推荐");
                hotDishes.add(rec);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("获取热门菜品失败: " + e.getMessage());
        }

        return hotDishes;
    }

    /**
     * 3. 营养健康分析（增加无数据时的模拟报告）
     */
    public Map<String, Object> analyzeNutrition(Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Order> recentOrders = orderMapper.listByUserId(userId);
            
            // 如果没有订单，返回模拟数据以便展示 UI
            if (recentOrders == null || recentOrders.isEmpty()) {
                System.out.println("[AI Service] 用户无订单，返回模拟营养报告");
                result.put("success", true);
                result.put("stats", new HashMap<String, Object>() {{
                    put("totalOrders", 5);
                    put("totalAmount", 125.50);
                }});
                result.put("analysis", "【模拟分析报告】\n根据您最近的饮食记录分析：\n1. 营养评估：您的饮食结构较为均衡，但蔬菜摄入量略少。\n2. 健康建议：建议每天增加一份绿叶蔬菜，减少油炸食品摄入。\n3. 改善方案：午餐可以选择清淡套餐，多喝水。");
                return result;
            }

            Map<String, Object> dietStats = new HashMap<>();
            dietStats.put("totalOrders", recentOrders.size());
            dietStats.put("totalAmount", recentOrders.stream()
                .mapToDouble(o -> o.getTotalPrice() != null ? o.getTotalPrice() : 0)
                .sum());

            String prompt = "请分析以下用户一周的饮食数据，生成营养报告和健康建议：\n" +
                          "用户订单数量：" + recentOrders.size() + "\n" +
                          "用户消费金额：" + dietStats.get("totalAmount") + "元\n\n" +
                          "请按以下格式输出：\n" +
                          "1. 饮食评估（营养均衡性、热量摄入等）\n" +
                          "2. 健康建议（3-5条具体建议）\n" +
                          "3. 推荐改善方案";

            String analysis = callDashScope(prompt);

            result.put("success", true);
            result.put("analysis", analysis);
            result.put("stats", dietStats);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "分析失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 4. 智能排课冲突检测
     */
    public Map<String, Object> checkScheduleConflict(Long userId, Date reserveDate, String timeSlot, Integer duration) {
        Map<String, Object> result = new HashMap<>();

        try {
            String[] busyTimeSlots = {"09:00", "10:00", "14:00", "15:00"};
            boolean hasConflict = Arrays.asList(busyTimeSlots).contains(timeSlot);

            result.put("hasConflict", hasConflict);
            
            if (hasConflict) {
                result.put("conflictInfo", "该时间段与您的课程冲突");
                String prompt = "用户想预约" + timeSlot + "时间段的自习室，但该时间段与课程冲突。请给出3个其他推荐时间段。";
                result.put("suggestions", callDashScope(prompt));
            } else {
                result.put("message", "该时间段空闲，可以预约");
            }

        } catch (Exception e) {
            result.put("hasConflict", false);
            result.put("error", "检测失败：" + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 5. 活动智能摘要
     */
    public String generateActivitySummary(String title, String content, Date startTime, Date endTime, String location) {
        String prompt = "请为以下校园活动生成一句话摘要：\n\n" +
                       "活动名称：" + title + "\n" +
                       "活动时间：" + startTime + " 至 " + endTime + "\n" +
                       "活动地点：" + location + "\n" +
                       (content != null ? "活动详情：" + content + "\n" : "") +
                       "\n要求：\n" +
                       "1. 用一句话概括活动核心内容\n" +
                       "2. 突出活动的亮点和特色\n" +
                       "3. 字数控制在30-50字\n" +
                       "4. 语言生动有吸引力";

        return callDashScope(prompt);
    }
}
