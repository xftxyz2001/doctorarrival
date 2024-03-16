package com.xftxyz.doctorarrival.hospital.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.annotation.NoWrap;
import com.xftxyz.doctorarrival.domain.hospital.HospitalSet;
import com.xftxyz.doctorarrival.hospital.service.HospitalSetService;
import com.xftxyz.doctorarrival.vo.hospital.HospitalSetQueryVO;
import com.xftxyz.doctorarrival.vo.hospital.HospitalStatisticVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "医院设置管理")
@RequestMapping("/admin/hospital/set")
public class HospitalSetAdminController {

    private final HospitalSetService hospitalSetService;

    @Operation(summary = "新增医院设置")
    @PostMapping("/save")
    public Boolean save(@RequestBody HospitalSet hospitalSet) {
        return hospitalSetService.saveWarp(hospitalSet);
    }

    @Operation(summary = "修改医院设置")
    @PutMapping("/update")
    public Boolean update(@RequestBody HospitalSet hospitalSet) {
        return hospitalSetService.updateByIdWarp(hospitalSet);
    }

    @Operation(summary = "删除医院设置")
    @DeleteMapping("/remove/id/{id}")
    public Boolean remove(@PathVariable("id") @Min(1) Long id) {
        return hospitalSetService.removeByIdWarp(id);
    }

    @Operation(summary = "批量删除医院设置")
    @DeleteMapping("/remove/batch")
    public Boolean removeBatch(@RequestBody @NotEmpty List<Long> idList) {
        return hospitalSetService.removeByIdsWarp(idList);
    }

    @Operation(summary = "通过ID获取医院设置")
    @GetMapping("/id/{id}")
    public HospitalSet getById(@PathVariable("id") @Min(1) Long id) {
        return hospitalSetService.getByIdWarp(id);
    }

    @Operation(summary = "条件查询医院设置带分页")
    @PostMapping("/find")
    public IPage<HospitalSet> find(@RequestBody HospitalSetQueryVO hospitalSetQueryVO,
                                   @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                   @RequestParam(value = "size", defaultValue = "20") @Min(1) Long size) {
        return hospitalSetService.find(hospitalSetQueryVO, current, size);
    }

    @Operation(summary = "设置医院状态")
    @PutMapping("/status/{id}/{status}")
    public Boolean setStatus(@PathVariable("id") @Min(1) Long id,
                             @PathVariable("status") @Min(0) @Max(1) Integer status) {
        return hospitalSetService.setStatus(id, status);
    }

    @NoWrap
    @Operation(summary = "医院数据统计")
    @PostMapping("/inner/statistic")
    public HospitalStatisticVO statistic(@RequestBody HospitalStatisticVO hospitalStatisticVO) {
        return hospitalSetService.statistic(hospitalStatisticVO);
    }

}
