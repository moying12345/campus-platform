package com.campus.service;
import com.campus.pojo.DishCategory;
import java.util.List;
public interface DishCategoryService {
    List<DishCategory> list();
    void add(DishCategory category);
    DishCategory getById(Integer id);
    void update(DishCategory category);
    void delete(Long id);
}