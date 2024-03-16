package com.xftxyz.doctorarrival.hospital.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.annotation.NoWrap;
import com.xftxyz.doctorarrival.domain.hospital.BookingRule;
import com.xftxyz.doctorarrival.hospital.service.HospitalService;
import com.xftxyz.doctorarrival.vo.hospital.HospitalQueryVO;
import com.xftxyz.doctorarrival.vo.hospital.HospitalVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "医院相关")
@RequestMapping("/api/hospital/find")
public class HospitalApiController {

    private final HospitalService hospitalService;

    @Operation(summary = "通过医院名称获取医院列表")
    @GetMapping("/name")
    public List<HospitalVO> findHospitalByHospitalName(@RequestParam("hospitalName") @NotBlank String hospitalName) {
        return hospitalService.findHospitalByHospitalName(hospitalName);
    }

    @Operation(summary = "通过医院编码获取医院")
    @GetMapping("/code/{hospitalCode}")
    public HospitalVO findHospitalByHospitalCode(@PathVariable("hospitalCode") @NotBlank String hospitalCode) {
        return hospitalService.findHospitalByHospitalCode(hospitalCode);
    }

    @Operation(summary = "条件查询医院带分页")
    @PostMapping("/page")
    public IPage<HospitalVO> findHospitalPage(@RequestBody @NotNull HospitalQueryVO hospitalQueryVO,
                                              @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                              @RequestParam(value = "size", defaultValue = "10") @Min(1) Long size) {
        return hospitalService.findHospitalPage(hospitalQueryVO, current, size);
    }

    @NoWrap
    @Operation(summary = "通过医院编码获取预约规则")
    @GetMapping("/rule/{hospitalCode}")
    public BookingRule getBookingRuleInner(@PathVariable("hospitalCode") @NotBlank String hospitalCode) {
        return hospitalService.getBookingRule(hospitalCode);
    }
}
