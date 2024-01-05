package com.xftxyz.doctorarrival.common.constant;

public interface Constants {

    // 短信验证码 Redis key 前缀
    String SMS_VERIFICATION_CODE_REDIS_KEY_PREFIX = "sms_verification_code_";
    // 短信验证码 Redis key 过期时间（秒）
    long SMS_VERIFICATION_CODE_REDIS_KEY_EXPIRE = 300L; // 5分钟
    // 验证码请求间隔时间（秒）
    long SMS_VERIFICATION_CODE_REQUEST_INTERVAL = 60L; // 1分钟
    // 短信验证码位数
    int SMS_VERIFICATION_CODE_LENGTH = 6;

}
