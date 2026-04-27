package com.campus.pojo;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

public class CampusActivity {
    private Long id;
    private String title;
    private String content;
    private String type;
    private String location;
    private String imageUrl;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startTime;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endTime;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date applyStartTime;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date applyEndTime;
    
    private Integer maxNum;
    private Integer applyNum;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public CampusActivity() {
        this.applyNum = 0;
        this.status = 1;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }
    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
    public Date getApplyStartTime() { return applyStartTime; }
    public void setApplyStartTime(Date applyStartTime) { this.applyStartTime = applyStartTime; }
    public Date getApplyEndTime() { return applyEndTime; }
    public void setApplyEndTime(Date applyEndTime) { this.applyEndTime = applyEndTime; }
    public Integer getMaxNum() { return maxNum != null ? maxNum : 0; }
    public void setMaxNum(Integer maxNum) { this.maxNum = maxNum; }
    public Integer getApplyNum() { return applyNum != null ? applyNum : 0; }
    public void setApplyNum(Integer applyNum) { this.applyNum = applyNum; }
    public Integer getStatus() { return status != null ? status : 1; }
    public void setStatus(Integer status) { this.status = status; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}