package com.campus.mapper;
import com.campus.pojo.MealPackage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MealPackageMapper {
    List<MealPackage> findAll();
    MealPackage findById(Long id);
    void add(MealPackage pkg);
    void update(MealPackage pkg);
    void updateStatus(@Param("id") Long id, @Param("status") int status);
    void delete(Long id);
}