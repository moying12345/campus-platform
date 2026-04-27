package com.campus.pojo;

import java.util.Date;

public class ReservationRule {
    private Long id;
    private String timeSlot; // 时段：上午/下午/晚上
    private Integer maxDuration; // 最大预约时长（小时）
    private Integer cancelAdvanceHours; // 提前取消时间（小时）
    private Date createTime;
    private Date updateTime;

    public ReservationRule() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public Integer getMaxDuration() { return maxDuration; }
    public void setMaxDuration(Integer maxDuration) { this.maxDuration = maxDuration; }
    public Integer getCancelAdvanceHours() { return cancelAdvanceHours; }
    public void setCancelAdvanceHours(Integer cancelAdvanceHours) { this.cancelAdvanceHours = cancelAdvanceHours; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}
