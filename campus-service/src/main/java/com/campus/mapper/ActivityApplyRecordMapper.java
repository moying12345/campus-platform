package com.campus.mapper;

import com.campus.pojo.ActivityApplyRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityApplyRecordMapper {
    List<ActivityApplyRecord> listByActivityId(@Param("activityId") Long activityId);
    ActivityApplyRecord getByActivityIdAndUserId(@Param("activityId") Long activityId, @Param("userId") Long userId);
    int insert(ActivityApplyRecord record);
    int updateStatus(@Param("activityId") Long activityId, @Param("userId") Long userId, @Param("status") Integer status);
    int countByActivityIdAndStatus(@Param("activityId") Long activityId, @Param("status") Integer status);
}
