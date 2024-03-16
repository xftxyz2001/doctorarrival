package com.xftxyz.doctorarrival.common.controller;

import com.xftxyz.doctorarrival.annotation.NoWrap;
import com.xftxyz.doctorarrival.common.service.DictService;
import com.xftxyz.doctorarrival.domain.common.Dict;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "数据字典相关")
@RequestMapping("/api/common/dict")
public class DictApiController {

    private final DictService dictService;

    @Operation(summary = "通过ID获取项",
               description = "根据传入的数据字典ID（最小值为1）获取对应的字典项信息。")
    @GetMapping("/id/{id}")
    public Dict getDictById(@PathVariable("id") @Min(1) Long id) {
        return dictService.getById(id);
    }

    @Operation(summary = "通过父项ID获取子项",
               description = "根据传入的父级数据字典ID（最小值为1）获取其所有的子级字典项信息。")
    @GetMapping("/children/id/{parentId}")
    public List<Dict> getDictChildrenByParentId(@PathVariable("parentId") @Min(1) Long parentId) {
        return dictService.getDictChildrenByParentId(parentId);
    }

    @Operation(summary = "通过字典编码获取子项",
               description = "根据传入的数据字典编码获取对应的子级字典项信息。")
    @GetMapping("/children/code/{dictCode}")
    public List<Dict> getDictChildrenByDictCode(@PathVariable("dictCode") String dictCode) {
        return dictService.getDictChildrenByDictCode(dictCode);
    }

    @NoWrap
    @Operation(summary = "通过字典编码获取子项以Map形式返回",
               description = "根据传入的数据字典编码，以键值对的形式返回对应的子级字典项信息集合。")
    @GetMapping("/inner/map/code/{dictCode}")
    public Map<String, String> getDictMapByDictCodeInner(@PathVariable("dictCode") String dictCode) {
        return dictService.getDictMapByDictCodeInner(dictCode);
    }

    @NoWrap
    @Operation(summary = "通过省市区编码获取省市区名称",
               description = "根据输入的省、市、区编码，返回对应的省市区名称列表。")
    @GetMapping("/inner/administrative/divisions/list")
    public List<String> getAdministrativeDivisionsListInner(@RequestParam("provinceCode") String provinceCode,
                                                            @RequestParam("cityCode") String cityCode,
                                                            @RequestParam("districtCode") String districtCode) {
        return dictService.getAdministrativeDivisionsListInner(provinceCode, cityCode, districtCode);
    }
}
