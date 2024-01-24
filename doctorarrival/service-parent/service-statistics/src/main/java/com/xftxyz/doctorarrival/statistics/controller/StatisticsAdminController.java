package com.xftxyz.doctorarrival.statistics.controller;

import com.xftxyz.doctorarrival.statistics.service.StatisticsService;
import com.xftxyz.doctorarrival.vo.hospital.HospitalStatisticVO;
import com.xftxyz.doctorarrival.vo.order.OrderStatisticVO;
import com.xftxyz.doctorarrival.vo.user.UserStatisticVO;
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
@RequestMapping("/admin/statistics/analysis")
public class StatisticsAdminController {

    private final StatisticsService statisticsService;

    @PostMapping("/hospital")
    public HospitalStatisticVO hospital(@RequestBody @NotNull HospitalStatisticVO hospitalStatisticVO) {
        return statisticsService.hospital(hospitalStatisticVO);
    }

    @PostMapping("/user")
    public UserStatisticVO user(@RequestBody @NotNull UserStatisticVO userStatisticVO) {
        return statisticsService.user(userStatisticVO);
    }

    @PostMapping("/order")
    public OrderStatisticVO order(@RequestBody @NotNull OrderStatisticVO orderStatisticVO) {
        return statisticsService.order(orderStatisticVO);
    }
}
