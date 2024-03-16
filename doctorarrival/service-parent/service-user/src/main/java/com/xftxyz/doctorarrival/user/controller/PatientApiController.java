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

    @Operation(summary = "获取当前用户添加的所有就诊人",
               description = "此接口用于获取经过JWT鉴权的用户ID所关联的所有就诊人信息。接口成功执行后返回一个包含就诊人信息的列表。")
    @GetMapping("/auth/list")
    public List<PatientVO> getPatientList(@RequestHeader(JwtHelper.X_USER_ID) String userId) {
        return patientService.getPatientList(userId);
    }

    @Operation(summary = "通过就诊人ID获取就诊人",
               description = "通过JWT鉴权的用户ID和指定的就诊人ID获取就诊人的详细信息。成功获取后返回一个包含就诊人完整信息的对象。")
    @GetMapping("/auth/detail/{patientId}")
    public PatientVO getPatientDetail(@RequestHeader(JwtHelper.X_USER_ID) String userId,
                                      @PathVariable("patientId") String patientId) {
        return patientService.getPatientDetail(userId, patientId);
    }

    @Operation(summary = "移除就诊人",
               description = "根据JWT鉴权的用户ID和指定的就诊人ID，删除该用户关联的就诊人记录。删除成功后返回true。")
    @DeleteMapping("/auth/remove/{patientId}")
    public Boolean removePatient(@RequestHeader(JwtHelper.X_USER_ID) String userId,
                                 @PathVariable("patientId") String patientId) {
        return patientService.removePatient(userId, patientId);
    }

    @Operation(summary = "添加就诊人",
               description = "接收一个非空的Patient对象作为新增就诊人数据，同时通过JWT鉴权的用户ID确定所属关系，成功保存则返回true。")
    @PostMapping("/auth/add")
    public Boolean addPatient(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                              @RequestBody @NotNull Patient patient) {
        return patientService.addPatient(userId, patient);
    }

    @Operation(summary = "修改就诊人",
               description = "接收一个非空的Patient对象作为更新就诊人数据，通过JWT鉴权的用户ID确认权限，根据患者ID进行更新操作，成功更新则返回true。")
    @PutMapping("/auth/update")
    public Boolean updatePatient(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                                 @RequestBody @NotNull Patient patient) {
        return patientService.updatePatient(userId, patient);
    }

    @NoWrap
    @Operation(summary = "通过就诊人ID获取就诊人详情",
               description = "此接口为内部使用，无需用户鉴权，直接通过就诊人ID获取就诊人的原始详细信息，成功获取后返回一个包含就诊人完整信息的对象。")
    @GetMapping("/inner/detail/{patientId}")
    public Patient getPatientDetailInner(@PathVariable("patientId") Long patientId) {
        return patientService.getPatientDetailNoWarp(patientId);
    }
}
