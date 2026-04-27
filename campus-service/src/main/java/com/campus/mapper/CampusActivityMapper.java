package com.campus.mapper;
import com.campus.pojo.CampusActivity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CampusActivityMapper {
    List<CampusActivity> list();
    List<CampusActivity> listByStatus(Integer status);
    CampusActivity getById(Long id);
    int insert(CampusActivity activity);
    int update(CampusActivity activity);
    int delete(Long id);
    List<CampusActivity> getHotActivities(int limit);
    List<CampusActivity> getActivitiesByUserId(Long userId);
}