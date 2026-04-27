package com.campus.mapper;
import com.campus.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    List<User> list();
    User getById(Long id);
    int insert(User user);
    int update(User user);
    int delete(Long id);
    User findByUsernameOrPhone(@Param("usernameOrPhone") String usernameOrPhone);
    User findByPhone(@Param("phone") String phone);
}