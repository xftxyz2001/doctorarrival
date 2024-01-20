package com.xftxyz.doctorarrival.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DictCodeEnum {

    ROOT("ROOT", "全部分类"),
    ADMINISTRATIVE_DIVISIONS("AdministrativeDivisions", "行政区划"),
    HOSPITAL_TYPE("HospitalType", "医院等级"),
    CERTIFICATES_TYPE("CertificatesType", "证件类型"),
    EDUCATION("Education", "学历"),
    NATION("Nation", "民族");

    private final String code;
    private final String name;

}
