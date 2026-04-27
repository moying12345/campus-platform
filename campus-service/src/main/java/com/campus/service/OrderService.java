package com.campus.service;

import com.campus.pojo.Order;
import java.util.List;

public interface OrderService {
    List<Order> list();
    Order getById(Long id);
    void add(Order order);
    void update(Order order);
    void delete(Long id);
}
