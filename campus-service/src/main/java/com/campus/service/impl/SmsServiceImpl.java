package com.campus.service.impl;

import com.campus.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CODE_PREFIX = "sms:code:";
    private static final String LIMIT_PREFIX = "sms:limit:";
    private static final int CODE_EXPIRE = 5;
    private static final int LIMIT_EXPIRE = 60;

    @Override
    public String generateCode(String phone) {
        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        String codeKey = CODE_PREFIX + phone;
        String limitKey = LIMIT_PREFIX + phone;

        redisTemplate.opsForValue().set(codeKey, code, CODE_EXPIRE, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(limitKey, "1", LIMIT_EXPIRE, TimeUnit.SECONDS);

        System.out.println("短信验证码 [" + phone + "]: " + code);

        return code;
    }

    @Override
    public boolean verifyCode(String phone, String code) {
        String codeKey = CODE_PREFIX + phone;
        String storedCode = (String) redisTemplate.opsForValue().get(codeKey);

        if (storedCode != null && storedCode.equals(code)) {
            redisTemplate.delete(codeKey);
            return true;
        }
        return false;
    }

    @Override
    public boolean canSendCode(String phone) {
        String limitKey = LIMIT_PREFIX + phone;
        Boolean exists = redisTemplate.hasKey(limitKey);
        return exists == null || !exists;
    }
}
