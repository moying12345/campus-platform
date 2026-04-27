package com.campus.mapper;
import com.campus.pojo.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrderInfoMapper {
    List<OrderInfo> list();
    OrderInfo getById(Long id);
    int insert(OrderInfo orderInfo);
    int update(OrderInfo orderInfo);
    int delete(Long id);
}