package com.xftxyz.doctorarrival.hospital.controller;

import com.xftxyz.doctorarrival.domain.hospital.Department;
import com.xftxyz.doctorarrival.hospital.service.DepartmentService;
import com.xftxyz.doctorarrival.vo.hospital.DepartmentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "科室相关")
@RequestMapping("/api/hospital/department")
public class DepartmentApiController {

    private final DepartmentService departmentService;

    @Operation(summary = "通过医院编码获取科室列表")
    @GetMapping("/hospital/code/{hospitalCode}")
    public List<DepartmentVO> getDepartmentByHospitalCode(@PathVariable("hospitalCode") @NotBlank String hospitalCode) {
        return departmentService.findDepartmentByHospitalCode(hospitalCode);
    }

    @Operation(summary = "通过医院编码和科室编码获取科室")
    @GetMapping("/hospital/department/code")
    public Department getDepartmentByHospitalCodeAndDepartmentCode(@RequestParam("hospitalCode") @NotBlank String hospitalCode,
                                                                   @RequestParam("departmentCode") @NotBlank String departmentCode) {
        return departmentService.findDepartmentByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
    }
}
