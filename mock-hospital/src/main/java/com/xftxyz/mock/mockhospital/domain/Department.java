package com.xftxyz.mock.mockhospital.domain;

import lombok.Data;

@Data
public class Department {
    /**
     * 科室编号
     */
    private String departmentCode;

    /**
     * 科室名称
     */
    private String departmentName;

    /**
     * 科室描述
     */
    private String intro;

    /**
     * 大科室编号
     */
    private String primaryDepartmentCode;

    /**
     * 大科室名称
     */
    private String primaryDepartmentName;
}
