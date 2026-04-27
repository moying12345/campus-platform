package com.campus.mapper;

import com.campus.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DishMapper {
    List<Dish> findAll();
    List<Dish> findByCategoryId(Long categoryId);
    void add(Dish dish);
    void update(Dish dish);
    Dish findById(Long id);
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);
    void delete(Long id);
}