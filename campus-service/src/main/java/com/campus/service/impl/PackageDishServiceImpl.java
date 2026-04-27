package com.campus.service.impl;

import com.campus.mapper.PackageDishMapper;
import com.campus.pojo.Dish;
import com.campus.pojo.PackageDish;
import com.campus.service.PackageDishService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PackageDishServiceImpl implements PackageDishService {

    @Resource
    private PackageDishMapper packageDishMapper;

    @Override
    public void add(PackageDish pd) {
        packageDishMapper.add(pd);
    }

    @Override
    public List<PackageDish> list() {
        return packageDishMapper.list();
    }

    @Override
    public List<Dish> findDishesByPackageId(Long packageId) {
        return packageDishMapper.findDishesByPackageId(packageId);
    }

    @Override
    public void delete(Long id) {
        packageDishMapper.delete(id);
    }

    @Override
    public void deleteByPackageId(Long packageId) {
        packageDishMapper.deleteByPackageId(packageId);
    }
}