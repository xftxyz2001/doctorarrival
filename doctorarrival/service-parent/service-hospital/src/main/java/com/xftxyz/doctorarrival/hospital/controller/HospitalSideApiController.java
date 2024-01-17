package com.xftxyz.doctorarrival.hospital.controller;

import com.xftxyz.doctorarrival.hospital.service.HospitalSideService;
import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import com.xftxyz.doctorarrival.vo.hospital.HospitalJoinVO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospital/side")
public class HospitalSideApiController {

    private final HospitalSideService hospitalSideService;

    // 医院加入
    @PostMapping("/join")
    public ResponseEntity<Resource> join(@RequestBody @Validated HospitalJoinVO hospitalJoinVO) {
        Resource resource = hospitalSideService.join(hospitalJoinVO);
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"private.key\"")
                .body(resource);
    }

    // 获取AES密钥
    @GetMapping("/key/{hospitalCode}")
    public String updateSecretKey(@PathVariable("hospitalCode") String hospitalCode) {
        return hospitalSideService.updateSecretKey(hospitalCode);
    }

    // 更新医院信息
    @PostMapping("/hospital")
    public String updateHospital(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.updateHospital(encryptionRequest);
    }

    // 更新科室信息
    @PostMapping("/department")
    public String updateDepartment(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.updateDepartment(encryptionRequest);
    }

    // 更新排班信息
    @PostMapping("/schedule")
    public String updateSchedule(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.updateSchedule(encryptionRequest);
    }

    // 删除科室信息
    @PostMapping("/remove/department")
    public String deleteDepartment(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.deleteDepartment(encryptionRequest);
    }

    // 删除排班信息
    @PostMapping("/remove/schedule")
    public String deleteSchedule(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.deleteSchedule(encryptionRequest);
    }

    // 批量更新科室信息
    @PostMapping("/departments")
    public String updateDepartments(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.updateDepartments(encryptionRequest);
    }

    // 批量更新排班信息
    @PostMapping("/schedules")
    public String updateSchedules(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.updateSchedules(encryptionRequest);
    }

    // 批量删除科室信息
    @PostMapping("/remove/departments")
    public String deleteDepartments(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.deleteDepartments(encryptionRequest);
    }

    // 批量删除排班信息
    @PostMapping("/remove/schedules")
    public String deleteSchedules(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.deleteSchedules(encryptionRequest);
    }
}
