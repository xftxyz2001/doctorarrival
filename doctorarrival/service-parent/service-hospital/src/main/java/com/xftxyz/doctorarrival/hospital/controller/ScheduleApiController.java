package com.xftxyz.doctorarrival.hospital.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.annotation.NoWrap;
import com.xftxyz.doctorarrival.domain.hospital.Schedule;
import com.xftxyz.doctorarrival.hospital.service.ScheduleService;
import com.xftxyz.doctorarrival.vo.hospital.ScheduleVO;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "通过医院编码和科室编码获取排班列表",
               description = "根据提供的医院编码（不能为空白字符）和科室编码（不能为空白字符），获取该医院和科室下所有的排班信息，返回Schedule对象列表。")
    @GetMapping("/hospital/department/code")
    public List<Schedule> getScheduleByHospitalCodeAndDepartmentCode(@RequestParam("hospitalCode") @NotBlank String hospitalCode,
                                                                     @RequestParam("departmentCode") @NotBlank String departmentCode) {
        return scheduleService.findScheduleByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
    }

    @Operation(summary = "分页查询指定医院和科室的排班信息",
               description = "根据提供的医院编码（不能为空白字符）、科室编码（不能为空白字符），以及可选的当前页码（默认为1，最小值为1）和每页大小（默认为7，最小值为1），返回分页后的排班信息列表，封装在IPage<ScheduleVO>对象中。")
    @GetMapping("/page")
    public IPage<ScheduleVO> getSchedulePage(@RequestParam("hospitalCode") @NotBlank String hospitalCode,
                                             @RequestParam("departmentCode") @NotBlank String departmentCode,
                                             @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                             @RequestParam(value = "size", defaultValue = "7") @Min(1) Long size) {
        return scheduleService.findSchedulePage(hospitalCode, departmentCode, current, size);
    }

    @Operation(summary = "通过医院编码、科室编码和工作日期获取排班",
               description = "根据提供的医院编码（不能为空白字符）、科室编码（不能为空白字符）和工作日期（不能为空白字符），获取指定日期内该医院和科室的排班信息，返回Schedule对象列表。")
    @GetMapping("/hospital/department/date")
    public List<Schedule> getScheduleByHospitalCodeAndDepartmentCodeAndWorkDate(@RequestParam("hospitalCode") @NotBlank String hospitalCode,
                                                                                @RequestParam("departmentCode") @NotBlank String departmentCode,
                                                                                @RequestParam("workDate") @NotBlank String workDate) {
        return scheduleService.findScheduleByHospitalCodeAndDepartmentCodeAndWorkDate(hospitalCode, departmentCode, workDate);
    }

    @Operation(summary = "通过ID获取排班详情",
               description = "根据提供的排班ID（不能为空白字符），获取排班详情信息，返回ScheduleVO对象。")
    @GetMapping("/id/{id}")
    public ScheduleVO getScheduleById(@PathVariable("id") @NotBlank String id) {
        return scheduleService.findScheduleById(id);
    }

    @NoWrap
    @Operation(summary = "通过ID获取排班详情（内部调用）",
               description = "内部使用的接口，根据提供的排班ID（不能为空白字符），获取排班详情信息，返回ScheduleVO对象。")
    @GetMapping("/inner/id/{id}")
    public ScheduleVO getScheduleByIdInner(@PathVariable("id") @NotBlank String id) {
        return scheduleService.findScheduleByIdNoWarp(id);
    }
}
