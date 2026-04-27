package com.campus.service;

public interface SmsService {
    String generateCode(String phone);
    boolean verifyCode(String phone, String code);
    boolean canSendCode(String phone);
}
