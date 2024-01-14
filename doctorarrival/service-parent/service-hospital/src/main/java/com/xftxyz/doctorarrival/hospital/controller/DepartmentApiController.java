package com.xftxyz.doctorarrival.hospital.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
