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

    @Operation(summary = "通过医院编码获取科室列表",
               description = "根据传入的医院编码（不能为空白字符）获取该医院下的所有科室列表信息，返回的是DepartmentVO对象列表。")
    @GetMapping("/hospital/code/{hospitalCode}")
    public List<DepartmentVO> getDepartmentByHospitalCode(@PathVariable("hospitalCode") @NotBlank String hospitalCode) {
        return departmentService.findDepartmentByHospitalCode(hospitalCode);
    }

    @Operation(summary = "通过医院编码和科室编码获取科室",
               description = "根据输入的医院编码（不能为空白字符）和科室编码（同样不能为空白字符）获取特定医院下的特定科室信息，返回的是Department对象。")
    @GetMapping("/hospital/department/code")
    public Department getDepartmentByHospitalCodeAndDepartmentCode(@RequestParam("hospitalCode") @NotBlank String hospitalCode,
                                                                   @RequestParam("departmentCode") @NotBlank String departmentCode) {
        return departmentService.findDepartmentByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
    }
}
