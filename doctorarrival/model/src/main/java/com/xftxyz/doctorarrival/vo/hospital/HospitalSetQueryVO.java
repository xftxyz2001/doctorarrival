package com.xftxyz.doctorarrival.vo.hospital;

import lombok.Data;

import java.util.Date;

@Data
public class HospitalSetQueryVO {

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 医院编码
     */
    private String hospitalCode;

    /**
     * 创建时间（开始）
     */
    private Date createTimeFrom;

    /**
     * 创建时间（结束）
     */
    private Date createTimeTo;
}
