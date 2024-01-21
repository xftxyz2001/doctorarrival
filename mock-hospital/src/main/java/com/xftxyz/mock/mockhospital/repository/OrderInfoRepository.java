package com.xftxyz.mock.mockhospital.repository;

import com.xftxyz.mock.mockhospital.domain.OrderInfo;

import java.util.List;
import java.util.function.Predicate;

public interface OrderInfoRepository {

    // 添加
    void save(OrderInfo orderInfo);

    // 删除
    void delete(Long id);

    // 修改
    void update(OrderInfo orderInfo);

    // 查询（传入一个过滤器）
    List<OrderInfo> query(Predicate<OrderInfo> filter);
}
