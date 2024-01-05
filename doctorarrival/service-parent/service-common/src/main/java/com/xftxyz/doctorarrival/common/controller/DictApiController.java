package com.xftxyz.doctorarrival.common.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xftxyz.doctorarrival.common.service.DictService;
import com.xftxyz.doctorarrival.domain.common.Dict;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

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
}
