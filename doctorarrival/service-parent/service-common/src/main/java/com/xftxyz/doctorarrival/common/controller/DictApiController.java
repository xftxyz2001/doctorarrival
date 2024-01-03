package com.xftxyz.doctorarrival.common.controller;

import com.xftxyz.doctorarrival.common.service.DictService;
import com.xftxyz.doctorarrival.domain.common.Dict;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
