package com.xftxyz.doctorarrival.hospital.repository;

import com.xftxyz.doctorarrival.domain.hospital.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    // 根据医院编号、科室编号、排班id查询排班
    Schedule findByHospitalCodeAndDepartmentCodeAndHospitalScheduleId(String hospitalCode, String departmentCode, String scheduleId);

    // 根据医院编号、科室编号、排班id删除排班
    void deleteByHospitalCodeAndDepartmentCodeAndHospitalScheduleId(String hospitalCode, String departmentCode, String hospitalScheduleId);
}
