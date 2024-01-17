package com.xftxyz.doctorarrival.order.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xftxyz.doctorarrival.common.exception.BusinessException;
import com.xftxyz.doctorarrival.common.result.ResultEnum;
import com.xftxyz.doctorarrival.domain.hospital.Schedule;
import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import com.xftxyz.doctorarrival.hospital.client.ScheduleApiClient;
import com.xftxyz.doctorarrival.order.mapper.OrderInfoMapper;
import com.xftxyz.doctorarrival.order.service.OrderInfoService;
import com.xftxyz.doctorarrival.vo.order.OrderInfoQueryVO;
import com.xftxyz.doctorarrival.vo.order.SubmitOrderParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author 25810
 * @description 针对表【order_info(订单表)】的数据库操作Service实现
 * @createDate 2024-01-04 21:23:18
 */
@Service
@RequiredArgsConstructor
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo>
        implements OrderInfoService {

    private final ScheduleApiClient scheduleApiClient;

    @Override
    public Boolean saveWarp(OrderInfo orderInfo) {
        if (baseMapper.insert(orderInfo) <= 0) {
            throw new BusinessException(ResultEnum.ORDER_SAVE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean removeByIdWarp(Long id) {
        if (baseMapper.deleteById(id) <= 0) {
            throw new BusinessException(ResultEnum.ORDER_DELETE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean removeByIdsWarp(List<Long> idList) {
        int num = baseMapper.deleteBatchIds(idList);
        if (num < idList.size()) {
            throw new BusinessException(ResultEnum.ORDER_DELETE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean updateByIdWarp(OrderInfo orderInfo) {
        OrderInfo existOrderInfo = baseMapper.selectById(orderInfo.getId());
        if (ObjectUtils.isEmpty(existOrderInfo)) {
            throw new BusinessException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (baseMapper.updateById(orderInfo) <= 0) {
            throw new BusinessException(ResultEnum.ORDER_UPDATE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean setStatus(Long id, Integer status) {
        OrderInfo existOrderInfo = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(existOrderInfo)) {
            throw new BusinessException(ResultEnum.ORDER_NOT_EXIST);
        }
        existOrderInfo.setOrderStatus(status);
        if (baseMapper.updateById(existOrderInfo) <= 0) {
            throw new BusinessException(ResultEnum.ORDER_STATUS_UPDATE_FAILED);
        }
        return true;
    }

    @Override
    public OrderInfo getByIdWarp(Long id) {
        OrderInfo orderInfo = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(orderInfo)) {
            throw new BusinessException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderInfo;
    }

    @Override
    public IPage<OrderInfo> find(OrderInfoQueryVO orderInfoQueryVO, Long current, Long size) {
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.and(StringUtils.hasText(orderInfoQueryVO.getHospitalCodeOrName()),
                wrapper -> wrapper.like(OrderInfo::getHospitalCode, orderInfoQueryVO.getHospitalCodeOrName())
                        .or().like(OrderInfo::getHospitalName, orderInfoQueryVO.getHospitalCodeOrName()));

        lambdaQueryWrapper.and(StringUtils.hasText(orderInfoQueryVO.getDepartmentCodeOrName()),
                wrapper -> wrapper.like(OrderInfo::getDepartmentCode, orderInfoQueryVO.getDepartmentCodeOrName())
                        .or().like(OrderInfo::getDepartmentName, orderInfoQueryVO.getDepartmentCodeOrName()));

        lambdaQueryWrapper.like(StringUtils.hasText(orderInfoQueryVO.getDoctorName()), OrderInfo::getDoctorName, orderInfoQueryVO.getDoctorName());

        lambdaQueryWrapper.and(StringUtils.hasText(orderInfoQueryVO.getPatientIdOrName()),
                wrapper -> wrapper.like(OrderInfo::getPatientId, orderInfoQueryVO.getPatientIdOrName())
                        .or().like(OrderInfo::getPatientName, orderInfoQueryVO.getPatientIdOrName()));

        lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(orderInfoQueryVO.getOrderStatus()), OrderInfo::getOrderStatus, orderInfoQueryVO.getOrderStatus());

        lambdaQueryWrapper.between(!ObjectUtils.isEmpty(orderInfoQueryVO.getCreateTimeFrom()) && !ObjectUtils.isEmpty(orderInfoQueryVO.getCreateTimeTo()),
                OrderInfo::getCreateTime, orderInfoQueryVO.getCreateTimeFrom(), orderInfoQueryVO.getCreateTimeTo());

        return baseMapper.selectPage(new Page<>(current, size), lambdaQueryWrapper);
    }

    @Override
    public Long submitOrder(Long userId, SubmitOrderParam submitOrderParam) {
        String scheduleId = submitOrderParam.getScheduleId();
        Long patientId = submitOrderParam.getPatientId();

        Schedule schedule = scheduleApiClient.getScheduleById(scheduleId);
        // Patient patient = patientApiClient.getPatientById(patientId);
        return null;
    }

    @Override
    public OrderInfo getOrderDetail(Long userId, Long orderId) {
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderInfo::getUserId, userId);
        lambdaQueryWrapper.eq(OrderInfo::getId, orderId);
        OrderInfo orderInfo = baseMapper.selectOne(lambdaQueryWrapper);
        if (ObjectUtils.isEmpty(orderInfo)) {
            throw new BusinessException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderInfo;
    }

    @Override
    public IPage<OrderInfo> getOrderList(Long userId, Long current, Long size) {
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderInfo::getUserId, userId);
        lambdaQueryWrapper.orderByDesc(OrderInfo::getUpdateTime); // 按更新时间倒序
        return baseMapper.selectPage(new Page<>(current, size), lambdaQueryWrapper);
    }

    @Override
    public Boolean cancelOrder(Long userId, Long orderId) {
        return null;
    }
}




