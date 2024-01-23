package com.xftxyz.doctorarrival.vo.sms;

import lombok.Data;

@Data
public class NotificationVO {

    // 手机号
    private String phoneNumber;

    // 就诊人姓名
    private String patientName;

    // 医院名称
    private String hospitalName;

    // 科室名称
    private String departmentName;

    // 医生姓名
    private String doctorName;

    // 预约日期
    private String reserveDate;
}
