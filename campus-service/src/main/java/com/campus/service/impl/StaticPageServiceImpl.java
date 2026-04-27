package com.campus.service.impl;

import com.campus.mapper.CampusActivityMapper;
import com.campus.pojo.CampusActivity;
import com.campus.service.StaticPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaticPageServiceImpl implements StaticPageService {

    @Autowired
    private Configuration freeMarkerConfiguration;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private CampusActivityMapper campusActivityMapper;

    private static final String STATIC_HTML_PATH = "/static/activities";

    @Override
    public void generateActivityDetailPage(CampusActivity activity) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateActivityListPage() {
        try {
            String realPath = servletContext.getRealPath(STATIC_HTML_PATH);
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            List<CampusActivity> hotActivities = campusActivityMapper.getHotActivities(10);
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("activities", hotActivities);

            Template listTemplate = freeMarkerConfiguration.getTemplate("activity_list.ftl");
            try (Writer out = new FileWriter(new File(realPath, "list.html"))) {
                listTemplate.process(dataModel, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteActivityPage(Long activityId) {
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
}
