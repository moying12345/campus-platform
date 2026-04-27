package com.campus.service;

import com.campus.mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    public List<Map<String, Object>> getUserRegisterTrend() {
        return statisticsMapper.getUserRegisterTrend();
    }

    public List<Map<String, Object>> getMerchantOrderRatio() {
        return statisticsMapper.getMerchantOrderRatio();
    }

    public List<Map<String, Object>> getStudyRoomUsageRate() {
        return statisticsMapper.getStudyRoomUsageRate();
    }

    public List<Map<String, Object>> getActivityHeatRanking() {
        return statisticsMapper.getActivityHeatRanking();
    }

    public Map<String, Object> getRealTimeStatistics() {
        return statisticsMapper.getRealTimeStatistics();
    }
}
