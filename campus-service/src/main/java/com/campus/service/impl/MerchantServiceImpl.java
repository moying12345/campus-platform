package com.campus.service.impl;

import com.campus.mapper.MerchantMapper;
import com.campus.pojo.Merchant;
import com.campus.service.MerchantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Resource
    private MerchantMapper merchantMapper;

    @Override
    public List<Merchant> list() {
        return merchantMapper.findAll();
    }

    @Override
    public void add(Merchant merchant) {
        merchantMapper.add(merchant);
    }

    @Override
    public Merchant getById(Long id) {
        return merchantMapper.findById(id);
    }

    @Override
    public void update(Merchant merchant) {
        merchantMapper.update(merchant);
    }
}