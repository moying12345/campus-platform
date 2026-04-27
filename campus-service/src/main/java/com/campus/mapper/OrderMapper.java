package com.campus.mapper;

import com.campus.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> list();
    Order getById(Long id);
    int insert(Order order);
    int update(Order order);
    int delete(Long id);
    
    /**
     * 根据用户ID查询订单列表
     */
    List<Order> listByUserId(@Param("userId") Long userId);
}
