package com.xftxyz.doctorarrival.vo.order;

import lombok.Data;

@Data
public class SubmitOrderParam {

    // 排班id
    private String scheduleId;

    // 就诊人id
    private Long patientId;
}
