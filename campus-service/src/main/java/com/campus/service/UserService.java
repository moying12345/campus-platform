package com.campus.service;
import com.campus.pojo.User;
import java.util.List;

public interface UserService {
    List<User> list();
    User getById(Long id);
    void add(User user);
    void update(User user);
    void delete(Long id);
    User findByUsernameOrPhone(String usernameOrPhone);
    User findByPhone(String phone);
    void updateStatus(Long id, Integer status);
}