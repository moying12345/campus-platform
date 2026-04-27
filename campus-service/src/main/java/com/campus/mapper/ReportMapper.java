package com.campus.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {

    @Select("SELECT " +
            "  m.merchant_name as seller_name, " +
            "  COUNT(*) as order_count, " +
            "  SUM(o.total_price) as total_amount, " +
            "  SUM(CASE WHEN o.status = '已退款' THEN 1 ELSE 0 END) as refund_count, " +
            "  (SUM(CASE WHEN o.status = '已退款' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) as refund_rate " +
            "FROM `order` o " +
            "LEFT JOIN dish d ON o.dish_id = d.id " +
            "LEFT JOIN merchant m ON d.merchant_id = m.id " +
            "WHERE o.create_time >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
            "  AND m.merchant_name IS NOT NULL " +
            "GROUP BY m.merchant_name " +
            "ORDER BY total_amount DESC")
    List<Map<String, Object>> getMonthlyReportData();

    @Select("SELECT " +
            "  ca.title, " +
            "  COUNT(*) as participant_count, " +
            "  ca.start_time, " +
            "  ca.end_time, " +
            "  ca.location " +
            "FROM activity_apply_record aar " +
            "LEFT JOIN campus_activity ca ON aar.activity_id = ca.id " +
            "WHERE ca.title IS NOT NULL " +
            "  AND aar.status = 1 " +
            "GROUP BY ca.id, ca.title, ca.start_time, ca.end_time, ca.location " +
            "ORDER BY participant_count DESC")
    List<Map<String, Object>> getActivityReportData();
}
