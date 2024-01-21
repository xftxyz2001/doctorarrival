package com.xftxyz.mock.mockhospital.repository.impl;

import com.xftxyz.mock.mockhospital.domain.OrderInfo;
import com.xftxyz.mock.mockhospital.repository.OrderInfoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class OrderInfoRepositoryImpl implements OrderInfoRepository {

    private List<OrderInfo> orderInfos = new ArrayList<>();

    @Override
    public void save(OrderInfo orderInfo) {
        orderInfos.add(orderInfo);
    }

    @Override
    public void delete(Long id) {
        orderInfos.removeIf(orderInfo -> orderInfo.getId().equals(id));
    }

    @Override
    public void update(OrderInfo orderInfo) {
        delete(orderInfo.getId());
        save(orderInfo);
    }

    @Override
    public List<OrderInfo> query(Predicate<OrderInfo> filter) {
        return orderInfos.stream().filter(filter).collect(Collectors.toList());
    }
}