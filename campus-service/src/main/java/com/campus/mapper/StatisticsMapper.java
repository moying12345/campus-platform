package com.campus.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsMapper {

    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d') as date, COUNT(*) as count " +
            "FROM user " +
            "WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
            "GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d') " +
            "ORDER BY date ASC")
    List<Map<String, Object>> getUserRegisterTrend();

    @Select("SELECT COALESCE(m.merchant_name, '其他') as name, COUNT(*) as value " +
            "FROM `order` o " +
            "LEFT JOIN dish d ON o.dish_id = d.id " +
            "LEFT JOIN merchant m ON d.merchant_id = m.id " +
            "WHERE o.create_time >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
            "GROUP BY m.merchant_name " +
            "HAVING name IS NOT NULL " +
            "ORDER BY value DESC")
    List<Map<String, Object>> getMerchantOrderRatio();

    @Select("SELECT DATE_FORMAT(reserve_date, '%H:00') as hour, COUNT(*) as count " +
            "FROM reservation " +
            "WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "  AND status = 1 " +
            "GROUP BY DATE_FORMAT(reserve_date, '%H:00') " +
            "ORDER BY hour ASC")
    List<Map<String, Object>> getStudyRoomUsageRate();

    @Select("SELECT ca.title as name, COUNT(*) as value " +
            "FROM activity_apply_record aar " +
            "LEFT JOIN campus_activity ca ON aar.activity_id = ca.id " +
            "WHERE ca.title IS NOT NULL " +
            "  AND aar.status = 1 " +
            "GROUP BY ca.id, ca.title " +
            "ORDER BY value DESC " +
            "LIMIT 10")
    List<Map<String, Object>> getActivityHeatRanking();

    @Select("SELECT " +
            "  COALESCE((SELECT COUNT(*) FROM user), 0) as totalUsers, " +
            "  COALESCE((SELECT COUNT(*) FROM `order`), 0) as totalOrders, " +
            "  COALESCE((SELECT COUNT(*) FROM `order` WHERE status = '已完成'), 0) as completedOrders, " +
            "  COALESCE((SELECT COUNT(*) FROM reservation WHERE status = 1), 0) as reservedRooms, " +
            "  COALESCE((SELECT COUNT(*) FROM campus_activity WHERE status = 1), 0) as activeActivities")
    Map<String, Object> getRealTimeStatistics();
}
