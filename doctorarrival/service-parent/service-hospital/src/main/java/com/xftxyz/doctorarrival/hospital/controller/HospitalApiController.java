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

    @Operation(summary = "通过医院名称获取医院列表",
               description = "根据输入的医院名称（不能为空白字符）搜索匹配的医院列表，返回HospitalVO对象列表。")
    @GetMapping("/name")
    public List<HospitalVO> findHospitalByHospitalName(@RequestParam("hospitalName") @NotBlank String hospitalName) {
        return hospitalService.findHospitalByHospitalName(hospitalName);
    }

    @Operation(summary = "通过医院编码获取医院",
               description = "根据提供的医院编码（不能为空白字符）获取对应医院的详细信息，返回HospitalVO对象。")
    @GetMapping("/code/{hospitalCode}")
    public HospitalVO findHospitalByHospitalCode(@PathVariable("hospitalCode") @NotBlank String hospitalCode) {
        return hospitalService.findHospitalByHospitalCode(hospitalCode);
    }

    @Operation(summary = "条件查询医院带分页",
               description = "接收一个非空的HospitalQueryVO对象作为查询条件，以及可选的当前页码（默认为1，最小值为1）和每页大小（默认为10，最小值为1），返回按条件分页查询的医院列表，封装在IPage<HospitalVO>对象中。")
    @PostMapping("/page")
    public IPage<HospitalVO> findHospitalPage(@RequestBody @NotNull HospitalQueryVO hospitalQueryVO,
                                              @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                              @RequestParam(value = "size", defaultValue = "10") @Min(1) Long size) {
        return hospitalService.findHospitalPage(hospitalQueryVO, current, size);
    }

    @NoWrap
    @Operation(summary = "通过医院编码获取预约规则",
               description = "根据提供的医院编码（不能为空白字符）获取该医院的预约规则，返回BookingRule对象。")
    @GetMapping("/rule/{hospitalCode}")
    public BookingRule getBookingRuleInner(@PathVariable("hospitalCode") @NotBlank String hospitalCode) {
        return hospitalService.getBookingRule(hospitalCode);
    }
}
