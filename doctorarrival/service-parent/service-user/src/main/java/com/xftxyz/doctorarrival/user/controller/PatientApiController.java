package com.xftxyz.doctorarrival.user.controller;

import com.xftxyz.doctorarrival.annotation.NoWrap;
import com.xftxyz.doctorarrival.domain.user.Patient;
import com.xftxyz.doctorarrival.helper.JwtHelper;
import com.xftxyz.doctorarrival.user.service.PatientService;
import com.xftxyz.doctorarrival.vo.user.PatientVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "就诊人相关")
@RequestMapping("/api/user/patient")
public class PatientApiController {

    private final PatientService patientService;

    @Operation(summary = "获取当前用户添加的所有就诊人")
    @GetMapping("/auth/list")
    public List<PatientVO> getPatientList(@RequestHeader(JwtHelper.X_USER_ID) String userId) {
        return patientService.getPatientList(userId);
    }

    @Operation(summary = "通过就诊人ID获取就诊人")
    @GetMapping("/auth/detail/{patientId}")
    public PatientVO getPatientDetail(@RequestHeader(JwtHelper.X_USER_ID) String userId,
                                      @PathVariable("patientId") String patientId) {
        return patientService.getPatientDetail(userId, patientId);
    }

    @Operation(summary = "移除就诊人")
    @DeleteMapping("/auth/remove/{patientId}")
    public Boolean removePatient(@RequestHeader(JwtHelper.X_USER_ID) String userId,
                                 @PathVariable("patientId") String patientId) {
        return patientService.removePatient(userId, patientId);
    }

    @Operation(summary = "添加就诊人")
    @PostMapping("/auth/add")
    public Boolean addPatient(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                              @RequestBody @NotNull Patient patient) {
        return patientService.addPatient(userId, patient);
    }

    @Operation(summary = "修改就诊人")
    @PutMapping("/auth/update")
    public Boolean updatePatient(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                                 @RequestBody @NotNull Patient patient) {
        return patientService.updatePatient(userId, patient);
    }

    @NoWrap
    @Operation(summary = "通过就诊人ID获取就诊人详情")
    @GetMapping("/inner/detail/{patientId}")
    public Patient getPatientDetailInner(@PathVariable("patientId") Long patientId) {
        return patientService.getPatientDetailNoWarp(patientId);
    }
}
