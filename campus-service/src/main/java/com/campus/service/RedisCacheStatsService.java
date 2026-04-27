package com.campus.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RedisCacheStatsService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_PREFIX = "cache:stats:";
    private static final String HIT_KEY = "hit";
    private static final String MISS_KEY = "miss";
    private static final String TOTAL_KEY = "total";

    public void recordHit(String cacheType) {
        String key = CACHE_PREFIX + cacheType + ":" + HIT_KEY;
        redisTemplate.opsForValue().increment(key);
        redisTemplate.opsForValue().increment(CACHE_PREFIX + cacheType + ":" + TOTAL_KEY);
    }

    public void recordMiss(String cacheType) {
        String key = CACHE_PREFIX + cacheType + ":" + MISS_KEY;
        redisTemplate.opsForValue().increment(key);
        redisTemplate.opsForValue().increment(CACHE_PREFIX + cacheType + ":" + TOTAL_KEY);
    }

    public Map<String, Object> getStats(String cacheType) {
        Map<String, Object> stats = new HashMap<>();
        String prefix = CACHE_PREFIX + cacheType + ":";

        Object hitObj = redisTemplate.opsForValue().get(prefix + HIT_KEY);
        Object missObj = redisTemplate.opsForValue().get(prefix + MISS_KEY);
        Object totalObj = redisTemplate.opsForValue().get(prefix + TOTAL_KEY);

        Long hit = hitObj != null ? ((Number) hitObj).longValue() : 0L;
        Long miss = missObj != null ? ((Number) missObj).longValue() : 0L;
        Long total = totalObj != null ? ((Number) totalObj).longValue() : 0L;

        stats.put("hit", hit);
        stats.put("miss", miss);
        stats.put("total", total);

        long hitCount = hit != null ? hit : 0;
        long totalCount = total != null ? total : 0;
        double hitRate = totalCount > 0 ? (double) hitCount / totalCount * 100 : 0;
        stats.put("hitRate", String.format("%.2f%%", hitRate));

        return stats;
    }

    public Map<String, Object> getAllStats() {
        Map<String, Object> allStats = new HashMap<>();
        String[] cacheTypes = {"dish:image", "merchant:logo", "user:avatar", "package:hot", "home:recommend"};

        for (String type : cacheTypes) {
            allStats.put(type, getStats(type));
        }

        return allStats;
    }

    public void resetStats(String cacheType) {
        String prefix = CACHE_PREFIX + cacheType + ":";
        redisTemplate.delete(prefix + HIT_KEY);
        redisTemplate.delete(prefix + MISS_KEY);
        redisTemplate.delete(prefix + TOTAL_KEY);
    }

    public void resetAllStats() {
        String[] cacheTypes = {"dish:image", "merchant:logo", "user:avatar", "package:hot", "home:recommend"};
        for (String type : cacheTypes) {
            resetStats(type);
        }
    }
}
