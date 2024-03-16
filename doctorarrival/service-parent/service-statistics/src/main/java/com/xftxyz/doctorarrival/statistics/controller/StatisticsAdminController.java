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

    @Operation(summary = "医院统计",
            description = "此接口用于对医院的相关数据进行统计分析。客户端需通过HTTP POST请求发送一个非空的HospitalStatisticVO对象，其中包含统计所需的筛选条件或指标定义。服务端接收到请求后，根据提供的参数执行相应的统计计算，然后返回一个包含医院统计数据的HospitalStatisticVO对象。")
    @PostMapping("/hospital")
    public HospitalStatisticVO hospital(@RequestBody @NotNull HospitalStatisticVO hospitalStatisticVO) {
        return statisticsService.hospital(hospitalStatisticVO);
    }

    @Operation(summary = "用户统计",
            description = "此接口用于对用户数据进行统计分析。客户端通过HTTP POST方法提交一个非空的UserStatisticVO对象，其中包含了用户统计所需的具体参数。服务器端根据接收到的参数执行用户行为、活跃度或其他相关维度的统计计算，并返回一个包含用户统计数据的UserStatisticVO对象。")
    @PostMapping("/user")
    public UserStatisticVO user(@RequestBody @NotNull UserStatisticVO userStatisticVO) {
        return statisticsService.user(userStatisticVO);
    }

    @Operation(summary = "订单统计",
            description = "此接口提供了对订单数据进行统计的功能。客户端使用HTTP POST方法发送一个非空的OrderStatisticVO对象作为请求体，该对象中包含了订单统计的各项参数。服务端接收到请求后，根据请求体中的参数进行订单数量、金额、类别等多维度的统计分析，并返回一个包含订单统计数据的OrderStatisticVO对象。")
    @PostMapping("/order")
    public OrderStatisticVO order(@RequestBody @NotNull OrderStatisticVO orderStatisticVO) {
        return statisticsService.order(orderStatisticVO);
    }
}
