package com.campus.service;
import com.campus.pojo.Merchant;
import java.util.List;
public interface MerchantService {
    List<Merchant> list();
    void add(Merchant merchant);
    Merchant getById(Long id);
    void update(Merchant merchant);
}