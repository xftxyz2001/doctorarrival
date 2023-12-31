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

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/common/dict")
public class DictApiController {

    private final DictService dictService;

    @GetMapping("/by/id/{id}")
    public Dict getDictById(@PathVariable("id") @Min(1) Integer id) {
        return dictService.getById(id);
    }
}
