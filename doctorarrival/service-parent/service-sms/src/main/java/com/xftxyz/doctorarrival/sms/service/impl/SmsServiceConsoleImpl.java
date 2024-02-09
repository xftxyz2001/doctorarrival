package com.xftxyz.doctorarrival.sms.service.impl;

import com.xftxyz.doctorarrival.constant.Constants;
import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.helper.RandomHelper;
import com.xftxyz.doctorarrival.result.ResultEnum;
import com.xftxyz.doctorarrival.sms.service.SmsService;
import com.xftxyz.doctorarrival.vo.sms.NotificationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

// @Service
@Slf4j
@RequiredArgsConstructor
public class SmsServiceConsoleImpl implements SmsService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean sendVerificationCode(String phoneNumber) {
        // Redis对应的key
        String redisKey = Constants.SMS_VERIFICATION_CODE_REDIS_KEY_PREFIX + phoneNumber;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(redisKey))) {
            // 获取key剩余时间
            Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
            if (!ObjectUtils.isEmpty(expire)) {
                // 获取剩余等待时间
                long waitTime = Constants.SMS_VERIFICATION_CODE_REQUEST_INTERVAL
                        - (Constants.SMS_VERIFICATION_CODE_REDIS_KEY_EXPIRE - expire);
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
        log.info("【医来】您的验证码为" + code + "，请于5分钟内填写。如非本人操作，请忽略本短信。");
        return true;
    }

    @Override
    public Boolean sendAppointmentReminder(NotificationVO notificationVO) {
        log.info("尊敬的" + notificationVO.getPatientName() + "，您预约就诊即将开始。");
        log.info("就诊信息：");
        log.info("医院：" + notificationVO.getHospitalName());
        log.info("科室：" + notificationVO.getDepartmentName());
        log.info("医生：" + notificationVO.getDoctorName());
        log.info("时间：" + notificationVO.getReserveDate());
        log.info("请准时前往医院就诊。如有疑问，请联系客服。");
        log.info("祝您就诊顺利！");
        return true;
    }
}
