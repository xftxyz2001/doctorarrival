package com.xftxyz.doctorarrival.sms.service;

public interface SmsService {

    // 发送短信验证码
    Boolean sendVerificationCode(String phoneNumber);

    // 发送预约提醒通知
    Boolean sendAppointmentReminder(String phoneNumber, String patientName, String appointmentDescription);


}
