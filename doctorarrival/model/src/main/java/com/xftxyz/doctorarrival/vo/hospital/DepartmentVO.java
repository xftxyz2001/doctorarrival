package com.xftxyz.doctorarrival.vo.hospital;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentVO {
    /**
     * 医院编号
     */
    private String hospitalCode;

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

    /**
     * 子科室
     */
    private List<DepartmentVO> children;
}
