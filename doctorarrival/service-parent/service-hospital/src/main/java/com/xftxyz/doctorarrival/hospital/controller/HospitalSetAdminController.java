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

    @Operation(summary = "新增医院设置",
               description = "接收一个非空的HospitalSet对象作为新增数据，成功保存则返回true。")
    @PostMapping("/save")
    public Boolean save(@RequestBody HospitalSet hospitalSet) {
        return hospitalSetService.saveWarp(hospitalSet);
    }

    @Operation(summary = "修改医院设置",
               description = "接收一个非空的HospitalSet对象作为更新数据，根据其ID进行更新操作，成功更新则返回true。")
    @PutMapping("/update")
    public Boolean update(@RequestBody HospitalSet hospitalSet) {
        return hospitalSetService.updateByIdWarp(hospitalSet);
    }

    @Operation(summary = "删除医院设置",
               description = "根据传入的ID（最小值为1）删除对应的医院设置，删除成功则返回true。")
    @DeleteMapping("/remove/id/{id}")
    public Boolean remove(@PathVariable("id") @Min(1) Long id) {
        return hospitalSetService.removeByIdWarp(id);
    }

    @Operation(summary = "批量删除医院设置",
               description = "接收一个非空的Long类型的ID列表，批量删除指定的医院设置，删除成功则返回true。")
    @DeleteMapping("/remove/batch")
    public Boolean removeBatch(@RequestBody @NotEmpty List<Long> idList) {
        return hospitalSetService.removeByIdsWarp(idList);
    }

    @Operation(summary = "通过ID获取医院设置",
               description = "根据传入的ID（最小值为1）获取对应的医院设置信息。")
    @GetMapping("/id/{id}")
    public HospitalSet getById(@PathVariable("id") @Min(1) Long id) {
        return hospitalSetService.getByIdWarp(id);
    }

    @Operation(summary = "条件查询医院设置带分页",
               description = "接收一个非空的HospitalSetQueryVO对象作为查询条件，以及可选的当前页码（默认为1，最小值为1）和每页大小（默认为20，最小值为1），返回符合条件的医院设置分页列表。")
    @PostMapping("/find")
    public IPage<HospitalSet> find(@RequestBody HospitalSetQueryVO hospitalSetQueryVO,
                                   @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                   @RequestParam(value = "size", defaultValue = "20") @Min(1) Long size) {
        return hospitalSetService.find(hospitalSetQueryVO, current, size);
    }

    @Operation(summary = "设置医院状态",
               description = "根据传入的医院ID（最小值为1）和状态（0或1）更新医院状态，成功更新则返回true。")
    @PutMapping("/status/{id}/{status}")
    public Boolean setStatus(@PathVariable("id") @Min(1) Long id,
                             @PathVariable("status") @Min(0) @Max(1) Integer status) {
        return hospitalSetService.setStatus(id, status);
    }

    @NoWrap
    @Operation(summary = "医院数据统计",
               description = "接收一个非空的HospitalStatisticVO对象，根据其中的参数进行医院数据统计计算，返回统计结果。")
    @PostMapping("/inner/statistic")
    public HospitalStatisticVO statistic(@RequestBody HospitalStatisticVO hospitalStatisticVO) {
        return hospitalSetService.statistic(hospitalStatisticVO);
    }

}
