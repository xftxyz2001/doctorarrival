package com.xftxyz.doctorarrival.hospital.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.annotation.NoWrap;
import com.xftxyz.doctorarrival.domain.hospital.Schedule;
import com.xftxyz.doctorarrival.hospital.service.ScheduleService;
import com.xftxyz.doctorarrival.vo.hospital.ScheduleVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "排班相关")
@RequestMapping("/api/hospital/schedule")
public class ScheduleApiController {

    private final ScheduleService scheduleService;

    @GetMapping("/hospital/department/code")
    public List<Schedule> getScheduleByHospitalCodeAndDepartmentCode(@RequestParam("hospitalCode") @NotBlank String hospitalCode,
                                                                     @RequestParam("departmentCode") @NotBlank String departmentCode) {
        return scheduleService.findScheduleByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
    }

    @GetMapping("/page")
    public IPage<ScheduleVO> getSchedulePage(@RequestParam("hospitalCode") @NotBlank String hospitalCode,
                                             @RequestParam("departmentCode") @NotBlank String departmentCode,
                                             @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                             @RequestParam(value = "size", defaultValue = "7") @Min(1) Long size) {
        return scheduleService.findSchedulePage(hospitalCode, departmentCode, current, size);
    }

    @GetMapping("/hospital/department/date")
    public List<Schedule> getScheduleByHospitalCodeAndDepartmentCodeAndWorkDate(@RequestParam("hospitalCode") @NotBlank String hospitalCode,
                                                                                @RequestParam("departmentCode") @NotBlank String departmentCode,
                                                                                @RequestParam("workDate") @NotBlank String workDate) {
        return scheduleService.findScheduleByHospitalCodeAndDepartmentCodeAndWorkDate(hospitalCode, departmentCode, workDate);
    }

    @GetMapping("/id/{id}")
    public ScheduleVO getScheduleById(@PathVariable("id") @NotBlank String id) {
        return scheduleService.findScheduleById(id);
    }

    @NoWrap
    @GetMapping("/inner/id/{id}")
    public ScheduleVO getScheduleByIdInner(@PathVariable("id") @NotBlank String id) {
        return scheduleService.findScheduleByIdNoWarp(id);
    }
}
