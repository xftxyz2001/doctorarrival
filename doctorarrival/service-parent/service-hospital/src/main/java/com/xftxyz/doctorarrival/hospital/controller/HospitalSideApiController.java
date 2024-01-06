package com.xftxyz.doctorarrival.hospital.controller;

import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.xftxyz.doctorarrival.hospital.service.HospitalSideService;
import com.xftxyz.doctorarrival.vo.hospital.HospitalJoinVO;

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

    // 更新医院信息
    @PostMapping("/hospital")
    public Boolean updateHospital(@RequestBody @NotNull EncryptionRequest encryptionRequest) {
        return hospitalSideService.updateHospital(encryptionRequest);
    }
}
