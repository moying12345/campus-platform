package com.campus.pojo;

import java.util.Date;

public class ActivityApplyRecord {
    private Long id;
    private Long activityId;
    private Long userId;
    private Date applyTime;
    private Integer status;

    public ActivityApplyRecord() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Date getApplyTime() { return applyTime; }
    public void setApplyTime(Date applyTime) { this.applyTime = applyTime; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
