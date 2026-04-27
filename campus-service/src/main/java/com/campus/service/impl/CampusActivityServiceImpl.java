package com.campus.service.impl;

import com.campus.mapper.ActivityApplyRecordMapper;
import com.campus.mapper.CampusActivityMapper;
import com.campus.pojo.ActivityApplyRecord;
import com.campus.pojo.CampusActivity;
import com.campus.service.CampusActivityService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

@Service
public class CampusActivityServiceImpl implements CampusActivityService {

    @Autowired
    private CampusActivityMapper campusActivityMapper;

    @Autowired
    private ActivityApplyRecordMapper activityApplyRecordMapper;

    @Autowired
    private Configuration freeMarkerConfiguration;

    @Autowired
    private ServletContext servletContext;

    private static final String STATIC_HTML_PATH = "/static/activities";

    @Override
    public List<CampusActivity> list() {
        return campusActivityMapper.list();
    }

    @Override
    public List<CampusActivity> listByStatus(Integer status) {
        return campusActivityMapper.listByStatus(status);
    }

    @Override
    public CampusActivity getById(Long id) {
        return campusActivityMapper.getById(id);
    }

    @Override
    public void add(CampusActivity activity) {
        activity.setStatus(1);
        activity.setApplyNum(0);
        if (activity.getMaxNum() == null || activity.getMaxNum() <= 0) {
            activity.setMaxNum(100);
        }
        activity.setCreateTime(new Date());
        activity.setUpdateTime(new Date());
        campusActivityMapper.insert(activity);
        generateStaticPages(activity);
    }

    @Override
    public void update(CampusActivity activity) {
        activity.setUpdateTime(new Date());
        campusActivityMapper.update(activity);
        CampusActivity updated = campusActivityMapper.getById(activity.getId());
        if (updated != null) {
            generateStaticPages(updated);
        }
    }

    @Override
    public void delete(Long id) {
        campusActivityMapper.delete(id);
        deleteStaticPage(id);
    }

    @Override
    public boolean applyActivity(Long activityId, Long userId) {
        CampusActivity activity = campusActivityMapper.getById(activityId);
        if (activity == null) {
            return false;
        }

        ActivityApplyRecord existRecord = activityApplyRecordMapper.getByActivityIdAndUserId(activityId, userId);
        if (existRecord != null && existRecord.getStatus() == 1) {
            return false;
        }

        int currentApplyNum = activityApplyRecordMapper.countByActivityIdAndStatus(activityId, 1);
        if (currentApplyNum >= activity.getMaxNum()) {
            return false;
        }

        Date now = new Date();
        if (activity.getApplyStartTime() != null && activity.getApplyStartTime().after(now)) {
            return false;
        }
        if (activity.getApplyEndTime() != null && activity.getApplyEndTime().before(now)) {
            return false;
        }

        if (existRecord != null && existRecord.getStatus() == 0) {
            activityApplyRecordMapper.updateStatus(activityId, userId, 1);
        } else {
            ActivityApplyRecord record = new ActivityApplyRecord();
            record.setActivityId(activityId);
            record.setUserId(userId);
            activityApplyRecordMapper.insert(record);
        }

        int newApplyNum = activityApplyRecordMapper.countByActivityIdAndStatus(activityId, 1);
        activity.setApplyNum(newApplyNum);
        campusActivityMapper.update(activity);
        generateStaticPages(activity);
        
        return true;
    }

    @Override
    public boolean cancelApply(Long activityId, Long userId) {
        ActivityApplyRecord record = activityApplyRecordMapper.getByActivityIdAndUserId(activityId, userId);
        if (record == null || record.getStatus() == 0) {
            return false;
        }

        activityApplyRecordMapper.updateStatus(activityId, userId, 0);
        
        CampusActivity activity = campusActivityMapper.getById(activityId);
        if (activity != null) {
            int applyNum = activityApplyRecordMapper.countByActivityIdAndStatus(activityId, 1);
            activity.setApplyNum(applyNum);
            campusActivityMapper.update(activity);
            generateStaticPages(activity);
        }
        
        return true;
    }

    @Override
    public boolean hasApplied(Long activityId, Long userId) {
        ActivityApplyRecord record = activityApplyRecordMapper.getByActivityIdAndUserId(activityId, userId);
        return record != null && record.getStatus() == 1;
    }

    @Override
    public List<CampusActivity> getActivitiesByUserId(Long userId) {
        return campusActivityMapper.getActivitiesByUserId(userId);
    }

    @Override
    public void generateStaticPages(CampusActivity activity) {
        try {
            String realPath = servletContext.getRealPath(STATIC_HTML_PATH);
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("activity", activity);

            Template detailTemplate = freeMarkerConfiguration.getTemplate("activity_detail.ftl");
            String detailFileName = "activity_" + activity.getId() + ".html";
            try (Writer out = new FileWriter(new File(realPath, detailFileName))) {
                detailTemplate.process(dataModel, out);
            }

            List<CampusActivity> hotActivities = campusActivityMapper.getHotActivities(10);
            dataModel.put("activities", hotActivities);
            Template listTemplate = freeMarkerConfiguration.getTemplate("activity_list.ftl");
            try (Writer out = new FileWriter(new File(realPath, "list.html"))) {
                listTemplate.process(dataModel, out);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteStaticPage(Long activityId) {
        try {
            String realPath = servletContext.getRealPath(STATIC_HTML_PATH);
            File file = new File(realPath, "activity_" + activityId + ".html");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateExpiredActivities() {
        List<CampusActivity> activities = campusActivityMapper.listByStatus(1);
        Date now = new Date();
        for (CampusActivity activity : activities) {
            if (activity.getApplyEndTime() != null && activity.getApplyEndTime().before(now)) {
                activity.setStatus(0);
                campusActivityMapper.update(activity);
                generateStaticPages(activity);
            }
        }
    }

    @Override
    public List<CampusActivity> getHotActivities(int limit) {
        return campusActivityMapper.getHotActivities(limit);
    }
}
