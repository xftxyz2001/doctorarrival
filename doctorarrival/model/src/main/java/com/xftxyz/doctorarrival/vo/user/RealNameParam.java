package com.xftxyz.doctorarrival.vo.user;

import lombok.Data;

@Data
public class RealNameParam {
    /**
     * 姓名
     */
    private String name;

    /**
     * 证件类型（1：身份证，2：户口本）
     */
    private Integer certificatesType;

    /**
     * 证件编号
     */
    private String certificatesNo;

    /**
     * 证件路径
     */
    private String certificatesUrl;
}
