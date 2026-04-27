package com.campus.mapper;
import com.campus.pojo.DishCategory;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DishCategoryMapper {
    List<DishCategory> findAll();
    void add(DishCategory category);
    DishCategory findById(Integer id);
    void update(DishCategory category);
    void delete(Long id);
}