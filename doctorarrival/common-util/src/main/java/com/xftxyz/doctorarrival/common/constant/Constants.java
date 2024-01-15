package com.xftxyz.doctorarrival.common.constant;

public interface Constants {

    // 加密算法
    String ALGORITHM_RSA = "RSA";
    String ALGORITHM_AES = "AES";

    // 密钥长度
    int RSA_KEY_SIZE = 2048;
    int AES_KEY_SIZE = 128;

    // 短信验证码 Redis key 前缀
    String SMS_VERIFICATION_CODE_REDIS_KEY_PREFIX = "sms_verification_code_"; // +手机号
    // 短信验证码 Redis key 过期时间（秒）
    long SMS_VERIFICATION_CODE_REDIS_KEY_EXPIRE = 300L; // 5分钟
    // 验证码请求间隔时间（秒）
    long SMS_VERIFICATION_CODE_REQUEST_INTERVAL = 60L; // 1分钟
    // 短信验证码位数
    int SMS_VERIFICATION_CODE_LENGTH = 6;

    // 医院接口对称加密密钥 Redis key 前缀
    String HOSPITAL_API_AES_KEY_REDIS_KEY_PREFIX = "hospital_api_aes_key_";// +医院code

    // 毫秒表示的秒/分/时/天
    long SECOND_IN_MILLIS = 1000L;
    long MINUTE_IN_MILLIS = 60 * SECOND_IN_MILLIS;
    long HOUR_IN_MILLIS = 60 * MINUTE_IN_MILLIS;
    long DAY_IN_MILLIS = 24 * HOUR_IN_MILLIS;

}
