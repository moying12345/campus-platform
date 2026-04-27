package com.campus.pojo;

import java.util.Date;

public class Merchant {
    private Long id;
    private String merchantName;
    private Long userId;
    private String address;
    private Integer status;
    private String logo;
    private Date createTime;
    private Date updateTime;

    // Getter/Setter（和User格式一致，省略重复部分，下同）
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMerchantName() { return merchantName; }
    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}