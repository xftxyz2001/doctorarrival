package com.xftxyz.doctorarrival.sms.service.impl;

import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.xftxyz.doctorarrival.constant.Constants;
import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.helper.RandomHelper;
import com.xftxyz.doctorarrival.result.ResultEnum;
import com.xftxyz.doctorarrival.sms.autoconfigure.SmsProperties;
import com.xftxyz.doctorarrival.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    private final StringRedisTemplate stringRedisTemplate;

    private final AsyncClient client;

    private final SmsProperties smsProperties;

    @Override
    public Boolean sendVerificationCode(String phoneNumber) {
        // Redis对应的key
        String redisKey = Constants.SMS_VERIFICATION_CODE_REDIS_KEY_PREFIX + phoneNumber;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(redisKey))) {
            // 获取key剩余时间
            Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
            if (!ObjectUtils.isEmpty(expire)) {
                // 获取剩余等待时间
                long waitTime = Constants.SMS_VERIFICATION_CODE_REQUEST_INTERVAL - (Constants.SMS_VERIFICATION_CODE_REDIS_KEY_EXPIRE - expire);
                if (waitTime > 0) {
                    throw new BusinessException(ResultEnum.SMS_VERIFICATION_CODE_REQUEST_TOO_FREQUENT);
                }
            }
        }
        // 生成验证码
        String code = RandomHelper.generateVerificationCode();
        // 存储验证码
        stringRedisTemplate.opsForValue().set(Constants.SMS_VERIFICATION_CODE_REDIS_KEY_PREFIX + phoneNumber, code,
                Constants.SMS_VERIFICATION_CODE_REDIS_KEY_EXPIRE, TimeUnit.SECONDS);
        // 发送验证码
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phoneNumbers(phoneNumber)
                .signName(smsProperties.getSignName())
                .templateCode(smsProperties.getTemplateCodeForVerificationCode())
                .templateParam("{\"code\":\"" + code + "\"}")
                .build();

        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        SendSmsResponse resp = null;
        try {
            resp = response.get();
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.SMS_VERIFICATION_CODE_SEND_FAILED);
        }
        if (!"OK".equals(resp.getBody().getCode())) {
            throw new BusinessException(ResultEnum.SMS_VERIFICATION_CODE_SEND_FAILED);
        }
        return true;
    }

    @Override
    public Boolean sendAppointmentReminder(String phoneNumber, String patientName, String appointmentDescription) {
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phoneNumbers(phoneNumber)
                .signName(smsProperties.getSignName())
                .templateCode(smsProperties.getTemplateCodeForAppointmentReminder())
                .templateParam("{\"name\":\"" + patientName + "\",\"description\":\"" + appointmentDescription + "\"}")
                .build();

        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        try {
            SendSmsResponse resp = response.get();
            return "OK".equals(resp.getBody().getCode());
        } catch (Exception e) {
            return false;
        }
    }
}
