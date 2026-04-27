package com.campus.service;

import com.campus.pojo.Dish;
import com.campus.pojo.MealPackage;
import com.campus.service.DishService;
import com.campus.service.MealPackageService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisHotDataCacheService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MealPackageService mealPackageService;

    @Resource
    private DishService dishService;

    @Resource
    private RedisCacheStatsService cacheStatsService;

    private static final String HOT_PACKAGES_KEY = "cache:package:hot";
    private static final String HOME_RECOMMEND_KEY = "cache:home:recommend";

    // 热点数据缓存时间：1小时
    private static final long HOT_DATA_EXPIRE = 1;

    // 缓存热门套餐
    public void cacheHotPackages() {
        List<MealPackage> packages = mealPackageService.list();
        if (packages != null && !packages.isEmpty()) {
            redisTemplate.opsForValue().set(HOT_PACKAGES_KEY, packages, HOT_DATA_EXPIRE, TimeUnit.HOURS);
        }
    }

    // 获取热门套餐
    @SuppressWarnings("unchecked")
    public List<MealPackage> getHotPackages() {
        List<MealPackage> packages = (List<MealPackage>) redisTemplate.opsForValue().get(HOT_PACKAGES_KEY);

        if (packages != null) {
            cacheStatsService.recordHit("package:hot");
            return packages;
        }

        cacheStatsService.recordMiss("package:hot");

        // 缓存未命中，从数据库查询并缓存
        packages = mealPackageService.list();
        if (packages != null && !packages.isEmpty()) {
            redisTemplate.opsForValue().set(HOT_PACKAGES_KEY, packages, HOT_DATA_EXPIRE, TimeUnit.HOURS);
        }

        return packages;
    }

    // 缓存首页推荐（例如：最新上架的菜品）
    public void cacheHomeRecommend() {
        List<Dish> dishes = dishService.list();
        if (dishes != null && !dishes.isEmpty()) {
            // 取前10个作为推荐
            List<Dish> recommendDishes = dishes.size() > 10 ? dishes.subList(0, 10) : dishes;
            redisTemplate.opsForValue().set(HOME_RECOMMEND_KEY, recommendDishes, HOT_DATA_EXPIRE, TimeUnit.HOURS);
        }
    }

    // 获取首页推荐
    @SuppressWarnings("unchecked")
    public List<Dish> getHomeRecommend() {
        List<Dish> dishes = (List<Dish>) redisTemplate.opsForValue().get(HOME_RECOMMEND_KEY);

        if (dishes != null) {
            cacheStatsService.recordHit("home:recommend");
            return dishes;
        }

        cacheStatsService.recordMiss("home:recommend");

        // 缓存未命中，从数据库查询并缓存
        dishes = dishService.list();
        if (dishes != null && !dishes.isEmpty()) {
            List<Dish> recommendDishes = dishes.size() > 10 ? dishes.subList(0, 10) : dishes;
            redisTemplate.opsForValue().set(HOME_RECOMMEND_KEY, recommendDishes, HOT_DATA_EXPIRE, TimeUnit.HOURS);
            return recommendDishes;
        }

        return dishes;
    }

    // 清除热点数据缓存
    public void clearHotDataCache() {
        redisTemplate.delete(HOT_PACKAGES_KEY);
        redisTemplate.delete(HOME_RECOMMEND_KEY);
    }

    // 刷新热点数据缓存
    public void refreshHotDataCache() {
        clearHotDataCache();
        cacheHotPackages();
        cacheHomeRecommend();
    }
}
