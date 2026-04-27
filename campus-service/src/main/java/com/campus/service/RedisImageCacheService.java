package com.campus.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisImageCacheService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedisCacheStatsService cacheStatsService;

    private static final String DISH_IMAGE_PREFIX = "dish:image:";
    private static final String MERCHANT_LOGO_PREFIX = "merchant:logo:";
    private static final String USER_AVATAR_PREFIX = "user:avatar:";
    
    private static final long IMAGE_CACHE_EXPIRE = 24;

    public void cacheDishImage(Long dishId, String imageUrl) {
        if (dishId == null || StringUtils.isEmpty(imageUrl)) {
            return;
        }
        String key = DISH_IMAGE_PREFIX + dishId;
        redisTemplate.opsForValue().set(key, imageUrl, IMAGE_CACHE_EXPIRE, TimeUnit.HOURS);
    }

    public String getDishImage(Long dishId) {
        if (dishId == null) {
            return null;
        }
        String key = DISH_IMAGE_PREFIX + dishId;
        String imageUrl = (String) redisTemplate.opsForValue().get(key);
        
        if (imageUrl != null) {
            cacheStatsService.recordHit("dish:image");
        } else {
            cacheStatsService.recordMiss("dish:image");
        }
        
        return imageUrl;
    }

    public void cacheMerchantLogo(Long merchantId, String logoUrl) {
        if (merchantId == null || StringUtils.isEmpty(logoUrl)) {
            return;
        }
        String key = MERCHANT_LOGO_PREFIX + merchantId;
        redisTemplate.opsForValue().set(key, logoUrl, IMAGE_CACHE_EXPIRE, TimeUnit.HOURS);
    }

    public String getMerchantLogo(Long merchantId) {
        if (merchantId == null) {
            return null;
        }
        String key = MERCHANT_LOGO_PREFIX + merchantId;
        String logoUrl = (String) redisTemplate.opsForValue().get(key);
        
        if (logoUrl != null) {
            cacheStatsService.recordHit("merchant:logo");
        } else {
            cacheStatsService.recordMiss("merchant:logo");
        }
        
        return logoUrl;
    }

    public void cacheUserAvatar(Long userId, String avatarUrl) {
        if (userId == null || StringUtils.isEmpty(avatarUrl)) {
            return;
        }
        String key = USER_AVATAR_PREFIX + userId;
        redisTemplate.opsForValue().set(key, avatarUrl, IMAGE_CACHE_EXPIRE, TimeUnit.HOURS);
    }

    public String getUserAvatar(Long userId) {
        if (userId == null) {
            return null;
        }
        String key = USER_AVATAR_PREFIX + userId;
        String avatarUrl = (String) redisTemplate.opsForValue().get(key);
        
        if (avatarUrl != null) {
            cacheStatsService.recordHit("user:avatar");
        } else {
            cacheStatsService.recordMiss("user:avatar");
        }
        
        return avatarUrl;
    }

    public void deleteDishImageCache(Long dishId) {
        if (dishId != null) {
            redisTemplate.delete(DISH_IMAGE_PREFIX + dishId);
        }
    }

    public void deleteMerchantLogoCache(Long merchantId) {
        if (merchantId != null) {
            redisTemplate.delete(MERCHANT_LOGO_PREFIX + merchantId);
        }
    }

    public void deleteUserAvatarCache(Long userId) {
        if (userId != null) {
            redisTemplate.delete(USER_AVATAR_PREFIX + userId);
        }
    }

    public void clearAllImageCache() {
        Set<String> keys = redisTemplate.keys(DISH_IMAGE_PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
        
        keys = redisTemplate.keys(MERCHANT_LOGO_PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
        
        keys = redisTemplate.keys(USER_AVATAR_PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
