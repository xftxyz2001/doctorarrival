package com.xftxyz.doctorarrival.hospital.controller;

import com.xftxyz.doctorarrival.annotation.NoWrap;
import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import com.xftxyz.doctorarrival.hospital.service.HospitalSideService;
import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import com.xftxyz.doctorarrival.vo.hospital.HospitalJoinVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "医院端相关")
@RequestMapping("/api/hospital/side")
public class HospitalSideApiController {

    private final HospitalSideService hospitalSideService;

    @Operation(summary = "医院申请加入",
               description = "接收一个经过验证的HospitalJoinVO对象作为医院申请加入的信息，成功申请后返回一个包含密钥的资源实体(Resource)，该资源将以附件形式下载，文件名格式为'hospitalCode.key'。")
    @PostMapping("/join")
    public ResponseEntity<Resource> join(@RequestBody @Validated HospitalJoinVO hospitalJoinVO) {
        Resource resource = hospitalSideService.join(hospitalJoinVO);
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + hospitalJoinVO.getHospitalCode() + ".key\"")
                .body(resource);
    }

    @Operation(summary = "更新对称加密密钥",
               description = "根据传入的医院编码更新该医院的对称加密密钥，成功更新后返回新密钥字符串。")
    @GetMapping("/key/{hospitalCode}")
    public String updateSecretKey(@PathVariable("hospitalCode") String hospitalCode) {
        return hospitalSideService.updateSecretKey(hospitalCode);
    }

    @Operation(summary = "更新医院信息",
               description = "接收一个非空的EncryptionRequest对象，其中包含加密过的医院信息更新请求，成功更新后返回处理结果字符串。")
    @PostMapping("/hospital")
    public String updateHospital(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.updateHospital(encryptionRequest);
    }

    @Operation(summary = "更新科室信息",
               description = "接收一个非空的EncryptionRequest对象，其中包含加密过的科室信息更新请求，成功更新后返回处理结果字符串。")
    @PostMapping("/department")
    public String updateDepartment(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.updateDepartment(encryptionRequest);
    }

    @Operation(summary = "更新排班信息",
               description = "接收一个非空的EncryptionRequest对象，其中包含加密过的排班信息更新请求，成功更新后返回处理结果字符串。")
    @PostMapping("/schedule")
    public String updateSchedule(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.updateSchedule(encryptionRequest);
    }

    @Operation(summary = "删除科室信息",
               description = "接收一个非空的EncryptionRequest对象，其中包含加密过的科室信息删除请求，成功删除后返回处理结果字符串。")
    @PostMapping("/remove/department")
    public String deleteDepartment(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.deleteDepartment(encryptionRequest);
    }

    @Operation(summary = "删除排班信息",
               description = "接收一个非空的EncryptionRequest对象，其中包含加密过的排班信息删除请求，成功删除后返回处理结果字符串。")
    @PostMapping("/remove/schedule")
    public String deleteSchedule(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.deleteSchedule(encryptionRequest);
    }

    @Operation(summary = "批量更新科室信息",
               description = "接收一个非空的EncryptionRequest对象，其中包含加密过的批量科室信息更新请求，成功更新后返回处理结果字符串。")
    @PostMapping("/departments")
    public String updateDepartments(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.updateDepartments(encryptionRequest);
    }

    @Operation(summary = "批量更新排班信息",
               description = "接收一个非空的EncryptionRequest对象，其中包含加密过的批量排班信息更新请求，成功更新后返回处理结果字符串。")
    @PostMapping("/schedules")
    public String updateSchedules(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.updateSchedules(encryptionRequest);
    }

    @Operation(summary = "批量删除科室信息",
               description = "接收一个非空的EncryptionRequest对象，其中包含加密过的批量科室信息删除请求，成功删除后返回处理结果字符串。")
    @PostMapping("/remove/departments")
    public String deleteDepartments(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.deleteDepartments(encryptionRequest);
    }

    @Operation(summary = "批量删除排班信息",
               description = "接收一个非空的EncryptionRequest对象，其中包含加密过的批量排班信息删除请求，成功删除后返回处理结果字符串。")
    @PostMapping("/remove/schedules")
    public String deleteSchedules(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.deleteSchedules(encryptionRequest);
    }

    @NoWrap
    @Operation(summary = "提交订单",
               description = "接收一个非空的OrderInfo对象作为订单提交信息，成功提交后返回true。")
    @PostMapping("/order")
    public Boolean submitOrderInner(@RequestBody @NotNull OrderInfo orderInfo) {
        return hospitalSideService.submitOrder(orderInfo);
    }

    @NoWrap
    @Operation(summary = "更新订单",
               description = "接收一个非空的OrderInfo对象作为订单状态更新信息，成功更新后返回true。")
    @PostMapping("/order/status")
    public Boolean updateOrderInner(@RequestBody @NotNull OrderInfo orderInfo) {
        return hospitalSideService.updateOrder(orderInfo);
    }
}
