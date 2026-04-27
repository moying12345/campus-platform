package com.campus.mapper;
import com.campus.pojo.Merchant;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MerchantMapper {
    List<Merchant> findAll();
    void add(Merchant merchant);
    Merchant findById(Long id);
    void update(Merchant merchant);
}