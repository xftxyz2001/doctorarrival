package com.xftxyz.doctorarrival.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xftxyz.doctorarrival.vo.order.OrderInfoQueryVO;

import java.util.List;

/**
* @author 25810
* @description 针对表【order_info(订单表)】的数据库操作Service
* @createDate 2024-01-04 21:23:18
*/
public interface OrderInfoService extends IService<OrderInfo> {

    Boolean saveWarp(OrderInfo orderInfo);

    Boolean removeByIdWarp(Long id);

    Boolean removeByIdsWarp(List<Long> idList);

    Boolean updateByIdWarp(OrderInfo orderInfo);

    Boolean setStatus(Long id, Integer status);

    OrderInfo getByIdWarp(Long id);

    IPage<OrderInfo> find(OrderInfoQueryVO orderInfoQueryVO, Long current, Long size);
}
