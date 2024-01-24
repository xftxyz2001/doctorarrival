package com.xftxyz.doctorarrival.statistics.service.impl;

import com.xftxyz.doctorarrival.hospital.client.HospitalSetAdminClient;
import com.xftxyz.doctorarrival.order.client.OrderInfoAdminClient;
import com.xftxyz.doctorarrival.statistics.service.StatisticsService;
import com.xftxyz.doctorarrival.user.client.UserInfoAdminClient;
import com.xftxyz.doctorarrival.vo.hospital.HospitalStatisticVO;
import com.xftxyz.doctorarrival.vo.order.OrderStatisticVO;
import com.xftxyz.doctorarrival.vo.user.UserStatisticVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final HospitalSetAdminClient hospitalSetAdminClient;
    private final UserInfoAdminClient userInfoAdminClient;
    private final OrderInfoAdminClient orderInfoAdminClient;

    @Override
    public HospitalStatisticVO hospital(HospitalStatisticVO hospitalStatisticVO) {
        return hospitalSetAdminClient.statistic(hospitalStatisticVO);
    }

    @Override
    public UserStatisticVO user(UserStatisticVO userStatisticVO) {
        return userInfoAdminClient.statistic(userStatisticVO);
    }

    @Override
    public OrderStatisticVO order(OrderStatisticVO orderStatisticVO) {
        return orderInfoAdminClient.statistic(orderStatisticVO);
    }
}
