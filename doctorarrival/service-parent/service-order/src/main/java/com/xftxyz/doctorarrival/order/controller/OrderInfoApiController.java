package com.xftxyz.doctorarrival.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import com.xftxyz.doctorarrival.order.service.OrderInfoService;
import com.xftxyz.doctorarrival.vo.order.OrderInfoQueryVO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order/info")
public class OrderInfoApiController {

    private final OrderInfoService orderInfoService;


}
