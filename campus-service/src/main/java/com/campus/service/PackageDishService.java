package com.campus.service;

import com.campus.pojo.Dish;
import com.campus.pojo.PackageDish;
import java.util.List;

public interface PackageDishService {
    void add(PackageDish pd);
    List<PackageDish> list();
    List<Dish> findDishesByPackageId(Long packageId);
    void delete(Long id);
    void deleteByPackageId(Long packageId);
}