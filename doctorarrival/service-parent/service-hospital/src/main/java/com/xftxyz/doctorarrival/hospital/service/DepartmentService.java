package com.xftxyz.doctorarrival.hospital.service;

import com.xftxyz.doctorarrival.domain.hospital.Department;
import com.xftxyz.doctorarrival.vo.hospital.DepartmentVO;

import java.util.List;

public interface DepartmentService {

    // 根据医院编号查询科室
    List<DepartmentVO> findDepartmentByHospitalCode(String hospitalCode);

    Department findDepartmentByHospitalCodeAndDepartmentCode(String hospitalCode, String departmentCode);
}
