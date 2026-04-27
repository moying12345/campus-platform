package com.campus.mapper;

import com.campus.pojo.Dish;
import com.campus.pojo.PackageDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PackageDishMapper {
    void add(PackageDish pd);
    List<PackageDish> list();
    List<Dish> findDishesByPackageId(@Param("packageId") Long packageId);
    void delete(Long id);
    void deleteByPackageId(@Param("packageId") Long packageId);
}