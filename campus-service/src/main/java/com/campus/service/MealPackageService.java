package com.campus.service;
import com.campus.pojo.MealPackage;
import java.util.List;
public interface MealPackageService {
    List<MealPackage> list();
    MealPackage getById(Long id);
    void add(MealPackage pkg);
    void update(MealPackage pkg);
    void updateStatus(Long id, int status);
    void delete(Long id);
}