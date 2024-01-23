package com.xftxyz.doctorarrival.sms.service;

import com.xftxyz.doctorarrival.vo.sms.NotificationVO;

public interface SmsService {

    // 发送短信验证码
    // 【医来】您的验证码为(code)，请于5分钟内填写。如非本人操作，请忽略本短信。
    Boolean sendVerificationCode(String phoneNumber);

    // 发送预约提醒通知
    // 尊敬的(patientName)，您预约就诊即将开始。
    // 就诊信息：
    // 医院：(hospitalName)
    // 科室：(departmentName)
    // 医生：(doctorName)
    // 时间：(reserveDate)
    // 请准时前往医院就诊。如有疑问，请联系客服。
    // 祝您就诊顺利！
    Boolean sendAppointmentReminder(NotificationVO notificationVO);
}
