package com.campus.service;

import com.campus.pojo.CampusActivity;

public interface StaticPageService {
    void generateActivityDetailPage(CampusActivity activity);
    void generateActivityListPage();
    void deleteActivityPage(Long activityId);
}
