package com.campus.service.impl;

import com.campus.mapper.MealPackageMapper;
import com.campus.pojo.MealPackage;
import com.campus.service.MealPackageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MealPackageServiceImpl implements MealPackageService {

    @Resource
    private MealPackageMapper mealPackageMapper;

    @Override
    public List<MealPackage> list() {
        return mealPackageMapper.findAll();
    }

    @Override
    public MealPackage getById(Long id) {
        return mealPackageMapper.findById(id);
    }

    @Override
    public void add(MealPackage pkg) {
        mealPackageMapper.add(pkg);
    }

    @Override
    public void update(MealPackage pkg) {
        mealPackageMapper.update(pkg);
    }

    @Override
    public void updateStatus(Long id, int status) {
        mealPackageMapper.updateStatus(id, status);
    }

    @Override
    public void delete(Long id) {
        mealPackageMapper.delete(id);
    }
}