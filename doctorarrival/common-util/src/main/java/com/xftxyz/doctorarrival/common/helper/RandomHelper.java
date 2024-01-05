package com.xftxyz.doctorarrival.common.helper;

import com.xftxyz.doctorarrival.common.constant.Constants;

import java.util.Random;

public class RandomHelper {

    private static final Random random = new Random();

    public static String generateVerificationCode() {
        // 生成验证码
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < Constants.SMS_VERIFICATION_CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
