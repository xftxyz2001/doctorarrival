package com.xftxyz.doctorarrival.vo.hospital;

import lombok.Data;

@Data
public class HospitalQueryVO {
    /**
     * 医院类型
     */
    private String hospitalType;

    /**
     * 省code
     */
    private String provinceCode;

    /**
     * 市code
     */
    private String cityCode;

    /**
     * 区code
     */
    private String districtCode;
}
