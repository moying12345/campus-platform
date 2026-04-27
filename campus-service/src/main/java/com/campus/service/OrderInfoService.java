package com.campus.service;

import com.campus.pojo.OrderInfo;
import java.util.List;

public interface OrderInfoService {
    List<OrderInfo> list();
    OrderInfo getById(Long id);
    void add(OrderInfo orderInfo);
    void update(OrderInfo orderInfo);
    void delete(Long id);
}