package com.xftxyz.doctorarrival.hospital.service;

import com.xftxyz.doctorarrival.domain.hospital.Department;
import com.xftxyz.doctorarrival.domain.hospital.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> findScheduleByHospitalCodeAndDepartmentCode(String hospitalCode, String departmentCode);
}
