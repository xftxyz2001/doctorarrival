package com.xftxyz.doctorarrival.hospital.service.impl;

import com.xftxyz.doctorarrival.domain.hospital.Schedule;
import com.xftxyz.doctorarrival.hospital.repository.ScheduleRepository;
import com.xftxyz.doctorarrival.hospital.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> findScheduleByHospitalCodeAndDepartmentCode(String hospitalCode, String departmentCode) {
        return scheduleRepository.findByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
    }
}
