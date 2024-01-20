package com.xftxyz.doctorarrival.hospital.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.domain.hospital.Hospital;
import com.xftxyz.doctorarrival.hospital.service.HospitalService;
import com.xftxyz.doctorarrival.vo.hospital.HospitalQueryVO;
import com.xftxyz.doctorarrival.vo.hospital.HospitalVO;
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
@RequestMapping("/api/hospital/find")
public class HospitalApiController {

    private final HospitalService hospitalService;

    // 根据医院名称返回医院列表
    @GetMapping("/name")
    public List<HospitalVO> findHospitalByHospitalName(@RequestParam("hospitalName") @NotBlank String hospitalName) {
        return hospitalService.findHospitalByHospitalName(hospitalName);
    }

    // 根据医院编号返回医院
    @GetMapping("/code/{hospitalCode}")
    public HospitalVO findHospitalByHospitalCode(@PathVariable("hospitalCode") @NotBlank String hospitalCode) {
        return hospitalService.findHospitalByHospitalCode(hospitalCode);
    }

    // 条件查询带分页
    @PostMapping("/page")
    public IPage<HospitalVO> findHospitalPage(@RequestBody @NotNull HospitalQueryVO hospitalQueryVO,
                                            @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                            @RequestParam(value = "size", defaultValue = "20") @Min(1) Long size) {
        return hospitalService.findHospitalPage(hospitalQueryVO, current, size);
    }


}
