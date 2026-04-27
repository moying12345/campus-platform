package com.campus.service.impl;

import com.campus.mapper.DishMapper;
import com.campus.pojo.Dish;
import com.campus.service.DishService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Resource
    private DishMapper dishMapper;

    @Override
    public PageInfo<Dish> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Dish> list = dishMapper.findAll();
        return new PageInfo<>(list);
    }

    @Override
    public List<Dish> list() {
        return dishMapper.findAll();
    }

    @Override
    public List<Dish> findByCategoryId(Long categoryId) {
        return dishMapper.findByCategoryId(categoryId);
    }

    @Override
    public void add(Dish dish) {
        dishMapper.add(dish);
    }

    @Override
    public void update(Dish dish) {
        dishMapper.update(dish);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        dishMapper.updateStatus(id, status);
    }

    @Override
    public void delete(Long id) {
        dishMapper.delete(id);
    }

    @Override
    public Dish getById(Long id) {
        return dishMapper.findById(id);
    }
}