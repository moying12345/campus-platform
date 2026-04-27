package com.campus.service.impl;

import com.campus.mapper.DishCategoryMapper;
import com.campus.pojo.DishCategory;
import com.campus.service.DishCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DishCategoryServiceImpl implements DishCategoryService {

    @Resource
    private DishCategoryMapper dishCategoryMapper;

    @Override
    public List<DishCategory> list() {
        return dishCategoryMapper.findAll();
    }

    @Override
    public void add(DishCategory category) {
        dishCategoryMapper.add(category);
    }

    @Override
    public DishCategory getById(Integer id) {
        return dishCategoryMapper.findById(id);
    }

    @Override
    public void update(DishCategory category) {
        dishCategoryMapper.update(category);
    }

    @Override
    public void delete(Long id) {
        dishCategoryMapper.delete(id);
    }
}