package com.campus.service;

import com.campus.pojo.CampusActivity;
import java.util.List;

public interface CampusActivityService {
    List<CampusActivity> list();
    List<CampusActivity> listByStatus(Integer status);
    CampusActivity getById(Long id);
    void add(CampusActivity activity);
    void update(CampusActivity activity);
    void delete(Long id);
    boolean applyActivity(Long activityId, Long userId);
    boolean cancelApply(Long activityId, Long userId);
    boolean hasApplied(Long activityId, Long userId);
    void generateStaticPages(CampusActivity activity);
    void updateExpiredActivities();
    List<CampusActivity> getHotActivities(int limit);
    List<CampusActivity> getActivitiesByUserId(Long userId);
}