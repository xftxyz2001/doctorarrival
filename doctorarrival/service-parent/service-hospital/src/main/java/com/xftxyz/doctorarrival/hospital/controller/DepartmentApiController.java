package com.xftxyz.doctorarrival.hospital.controller;

import java.util.List;

import com.xftxyz.doctorarrival.domain.hospital.Department;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.xftxyz.doctorarrival.hospital.service.DepartmentService;
import com.xftxyz.doctorarrival.vo.hospital.DepartmentVO;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospital/department")
public class DepartmentApiController {

    private final DepartmentService departmentService;

    @GetMapping("/hospital/code/{hospitalCode}")
    public List<DepartmentVO> getDepartmentByHospitalCode(@PathVariable("hospitalCode") @NotBlank String hospitalCode) {
        return departmentService.findDepartmentByHospitalCode(hospitalCode);
    }

    @GetMapping("/hospital/department/code")
    public Department getDepartmentByHospitalCodeAndDepartmentCode(@RequestParam("hospitalCode") @NotBlank String hospitalCode,
                                                                   @RequestParam("departmentCode") @NotBlank String departmentCode) {
        return departmentService.findDepartmentByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
    }
}
