package com.campus.service;

import com.campus.pojo.Dish;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface DishService {
    PageInfo<Dish> findPage(Integer pageNum, Integer pageSize);
    List<Dish> list();
    List<Dish> findByCategoryId(Long categoryId);
    void add(Dish dish);
    void update(Dish dish);
    void updateStatus(Integer id, Integer status);
    void delete(Long id);
    Dish getById(Long id);
}