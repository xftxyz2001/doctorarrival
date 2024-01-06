package com.xftxyz.doctorarrival.hospital.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xftxyz.doctorarrival.hospital.service.HospitalSideService;
import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import com.xftxyz.doctorarrival.vo.hospital.HospitalJoinVO;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

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
}
