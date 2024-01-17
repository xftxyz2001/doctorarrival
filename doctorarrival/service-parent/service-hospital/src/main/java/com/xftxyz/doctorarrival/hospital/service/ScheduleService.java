package com.xftxyz.doctorarrival.hospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.domain.hospital.Schedule;
import com.xftxyz.doctorarrival.vo.hospital.ScheduleDateVO;

import java.util.List;

public interface ScheduleService {
    List<Schedule> findScheduleByHospitalCodeAndDepartmentCode(String hospitalCode, String departmentCode);

    IPage<ScheduleDateVO> findSchedulePage(String hospitalCode, String departmentCode, Long current, Long size);

    List<Schedule> findScheduleByHospitalCodeAndDepartmentCodeAndWorkDate(String hospitalCode, String departmentCode, String workDate);

    Schedule findScheduleById(String id);

    Schedule findScheduleByIdNoWarp(String id);
}
