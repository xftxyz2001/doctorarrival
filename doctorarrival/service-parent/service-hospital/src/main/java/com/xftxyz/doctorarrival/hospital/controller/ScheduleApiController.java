package com.xftxyz.doctorarrival.hospital.controller;

import com.xftxyz.doctorarrival.domain.hospital.Department;
import com.xftxyz.doctorarrival.domain.hospital.Schedule;
import com.xftxyz.doctorarrival.hospital.service.ScheduleService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospital/schedule")
public class ScheduleApiController {

    private final ScheduleService scheduleService;

    @GetMapping("/hospital/department/code")
    public List<Schedule> getScheduleByHospitalCodeAndDepartmentCode(@RequestParam("hospitalCode") @NotBlank String hospitalCode,
                                                                     @RequestParam("departmentCode") @NotBlank String departmentCode) {
        return scheduleService.findScheduleByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
    }
}
