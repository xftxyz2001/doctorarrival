package com.xftxyz.doctorarrival.statistics.controller;

import com.xftxyz.doctorarrival.statistics.service.StatisticsService;
import com.xftxyz.doctorarrival.vo.hospital.HospitalStatisticVO;
import com.xftxyz.doctorarrival.vo.order.OrderStatisticVO;
import com.xftxyz.doctorarrival.vo.user.UserStatisticVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "统计分析")
@RequestMapping("/admin/statistics/analysis")
public class StatisticsAdminController {

    private final StatisticsService statisticsService;

    @Operation(summary = "医院统计")
    @PostMapping("/hospital")
    public HospitalStatisticVO hospital(@RequestBody @NotNull HospitalStatisticVO hospitalStatisticVO) {
        return statisticsService.hospital(hospitalStatisticVO);
    }

    @Operation(summary = "用户统计")
    @PostMapping("/user")
    public UserStatisticVO user(@RequestBody @NotNull UserStatisticVO userStatisticVO) {
        return statisticsService.user(userStatisticVO);
    }

    @Operation(summary = "订单统计")
    @PostMapping("/order")
    public OrderStatisticVO order(@RequestBody @NotNull OrderStatisticVO orderStatisticVO) {
        return statisticsService.order(orderStatisticVO);
    }
}
