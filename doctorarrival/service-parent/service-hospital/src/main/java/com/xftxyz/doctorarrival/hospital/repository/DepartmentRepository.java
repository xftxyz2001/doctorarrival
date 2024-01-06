package com.xftxyz.doctorarrival.hospital.repository;

import com.xftxyz.doctorarrival.domain.hospital.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, String> {
    // 根据医院编号和科室编号查询科室
    Department findByHospitalCodeAndDepartmentCode(String hospitalCode, String departmentCode);

    // 根据医院编号和科室编号删除科室
    void deleteByHospitalCodeAndDepartmentCode(String hospitalCode, String departmentCode);
}
