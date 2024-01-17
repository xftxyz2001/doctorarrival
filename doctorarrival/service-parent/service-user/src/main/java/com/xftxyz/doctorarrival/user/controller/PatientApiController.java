package com.xftxyz.doctorarrival.user.controller;

import com.xftxyz.doctorarrival.annotation.NoWrap;
import com.xftxyz.doctorarrival.helper.JwtHelper;
import com.xftxyz.doctorarrival.domain.user.Patient;
import com.xftxyz.doctorarrival.user.service.PatientService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/patient")
public class PatientApiController {

    private final PatientService patientService;

    // 列出当前用户下所有就诊人
    @GetMapping("/auth/list")
    public List<Patient> getPatientList(@RequestHeader(JwtHelper.X_USER_ID) String userId) {
        return patientService.getPatientList(userId);
    }

    // 根据就诊人id获取就诊人
    @GetMapping("/auth/detail/{patientId}")
    public Patient getPatientDetail(@RequestHeader(JwtHelper.X_USER_ID) String userId,
                                    @PathVariable("patientId") String patientId) {
        return patientService.getPatientDetail(userId, patientId);
    }

    // 移除就诊人
    @DeleteMapping("/auth/remove/{patientId}")
    public Boolean removePatient(@RequestHeader(JwtHelper.X_USER_ID) String userId,
                                 @PathVariable("patientId") String patientId) {
        return patientService.removePatient(userId, patientId);
    }

    // 添加就诊人
    @PostMapping("/auth/add")
    public Boolean addPatient(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                              @RequestBody @NotNull Patient patient) {
        return patientService.addPatient(userId, patient);
    }

    // 修改就诊人
    @PutMapping("/auth/update")
    public Boolean updatePatient(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                                 @RequestBody @NotNull Patient patient) {
        return patientService.updatePatient(userId, patient);
    }

    @NoWrap
    @GetMapping("/inner/detail/{patientId}")
    public Patient getPatientDetailInner(@PathVariable("patientId") Long patientId) {
        return patientService.getPatientDetailNoWarp(patientId);
    }
}
