package com.xftxyz.doctorarrival.sdk.vo.api;

import lombok.Data;

/**
 * 更新科室请求
 */
@Data
public class UpdateDepartmentRequest {
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
