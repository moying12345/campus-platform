package com.campus.pojo;

public class Permission {
    private Long id;
    private String permissionName;
    private String permissionCode;
    private String resourceType;
    private String url;
    private Long parentId;

    public Permission() {}

    public Permission(Long id, String permissionName, String permissionCode, String resourceType, String url, Long parentId) {
        this.id = id;
        this.permissionName = permissionName;
        this.permissionCode = permissionCode;
        this.resourceType = resourceType;
        this.url = url;
        this.parentId = parentId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPermissionName() { return permissionName; }
    public void setPermissionName(String permissionName) { this.permissionName = permissionName; }
    public String getPermissionCode() { return permissionCode; }
    public void setPermissionCode(String permissionCode) { this.permissionCode = permissionCode; }
    public String getResourceType() { return resourceType; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
}
