package com.xftxyz.doctorarrival.common.controller;

import com.xftxyz.doctorarrival.annotation.NoWrap;
import com.xftxyz.doctorarrival.common.service.DictService;
import com.xftxyz.doctorarrival.domain.common.Dict;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/common/dict")
public class DictApiController {

    private final DictService dictService;

    @GetMapping("/id/{id}")
    public Dict getDictById(@PathVariable("id") @Min(1) Long id) {
        return dictService.getById(id);
    }

    @GetMapping("/children/id/{parentId}")
    public List<Dict> getDictChildrenByParentId(@PathVariable("parentId") @Min(1) Long parentId) {
        return dictService.getDictChildrenByParentId(parentId);
    }

    @GetMapping("/children/code/{dictCode}")
    public List<Dict> getDictChildrenByDictCode(@PathVariable("dictCode") String dictCode) {
        return dictService.getDictChildrenByDictCode(dictCode);
    }

    @NoWrap
    @GetMapping("/inner/map/code/{dictCode}")
    public Map<String, String> getDictMapByDictCodeInner(@PathVariable("dictCode") String dictCode) {
        return dictService.getDictMapByDictCodeInner(dictCode);
    }

    @NoWrap
    @GetMapping("/inner/administrative/divisions/list")
    public List<String> getAdministrativeDivisionsListInner(@RequestParam("provinceCode") String provinceCode,
                                                            @RequestParam("cityCode") String cityCode,
                                                            @RequestParam("districtCode") String districtCode) {
        return dictService.getAdministrativeDivisionsListInner(provinceCode, cityCode, districtCode);
    }
}
