package com.xftxyz.doctorarrival.statistics.service;

import com.xftxyz.doctorarrival.vo.hospital.HospitalStatisticVO;
import com.xftxyz.doctorarrival.vo.order.OrderStatisticVO;
import com.xftxyz.doctorarrival.vo.user.UserStatisticVO;

public interface StatisticsService {
    HospitalStatisticVO hospital(HospitalStatisticVO hospitalStatisticVO);

    UserStatisticVO user(UserStatisticVO userStatisticVO);

    OrderStatisticVO order(OrderStatisticVO orderStatisticVO);
}
