package com.xftxyz.doctorarrival.order.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xftxyz.doctorarrival.domain.hospital.BookingRule;
import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import com.xftxyz.doctorarrival.domain.user.Patient;
import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.helper.DateTimeHelper;
import com.xftxyz.doctorarrival.hospital.client.HospitalApiClient;
import com.xftxyz.doctorarrival.hospital.client.HospitalSideApiClient;
import com.xftxyz.doctorarrival.hospital.client.ScheduleApiClient;
import com.xftxyz.doctorarrival.order.mapper.OrderInfoMapper;
import com.xftxyz.doctorarrival.order.service.OrderInfoService;
import com.xftxyz.doctorarrival.result.ResultEnum;
import com.xftxyz.doctorarrival.user.client.PatientApiClient;
import com.xftxyz.doctorarrival.vo.hospital.ScheduleVO;
import com.xftxyz.doctorarrival.vo.order.OrderInfoQueryParam;
import com.xftxyz.doctorarrival.vo.order.OrderInfoQueryVO;
import com.xftxyz.doctorarrival.vo.order.SubmitOrderParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
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

    private final HospitalApiClient hospitalApiClient;
    private final ScheduleApiClient scheduleApiClient;
    private final HospitalSideApiClient hospitalSideApiClient;

    private final PatientApiClient patientApiClient;

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

        ScheduleVO schedule = scheduleApiClient.getScheduleByIdInner(scheduleId);
        if (ObjectUtils.isEmpty(schedule)) {
            throw new BusinessException(ResultEnum.SCHEDULE_NOT_FOUND);
        }

        BookingRule bookingRule = hospitalApiClient.getBookingRuleInner(schedule.getHospitalCode());
        if (ObjectUtils.isEmpty(bookingRule)) {
            throw new BusinessException(ResultEnum.HOSPITAL_RULE_ERROR);
        }
        // 检查预约信息
        checkOrderInfo(schedule, bookingRule);

        Patient patient = patientApiClient.getPatientDetailInner(patientId);
        if (ObjectUtils.isEmpty(patient)) {
            throw new BusinessException(ResultEnum.PATIENT_NOT_EXIST);
        }

        // 构造订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(userId);
        orderInfo.setHospitalCode(schedule.getHospitalCode());
        orderInfo.setHospitalName(schedule.getHospitalName());
        orderInfo.setDepartmentCode(schedule.getDepartmentCode());
        orderInfo.setDepartmentName(schedule.getDepartmentName());
        orderInfo.setDoctorName(schedule.getDoctorName());
        orderInfo.setDoctorTitle(schedule.getDoctorTitle());
        orderInfo.setScheduleId(schedule.getHospitalScheduleId());
        orderInfo.setReserveDate(schedule.getWorkDate());

        orderInfo.setPatientId(patientId);
        orderInfo.setPatientName(patient.getName());
        orderInfo.setPatientPhone(patient.getPhone());
        orderInfo.setAmount(schedule.getAmount());
        orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_UNPAID);

        // 通知医院
        hospitalSideApiClient.submitOrderInner(orderInfo);

        if (baseMapper.insert(orderInfo) <= 0) {
            throw new BusinessException(ResultEnum.ORDER_SAVE_FAILED);
        }
        return orderInfo.getId();
    }

    private void checkOrderInfo(ScheduleVO schedule, BookingRule bookingRule) {
        Date workDate = schedule.getWorkDate();
        Integer availableNumber = schedule.getAvailableNumber();

        String stopTime = bookingRule.getStopTime();
        String releaseTime = bookingRule.getReleaseTime();
        Integer cycle = bookingRule.getCycle();

        Date today = DateTimeHelper.getTodayStartDate();
        // 过去的日期
        if (workDate.before(today)) {
            throw new BusinessException(ResultEnum.NOT_IN_RESERVATION_TIME);
        }
        String now = DateTimeHelper.getCurrentTime("hh:mm");
        // 今天已过停挂时间
        if (workDate.equals(today) && now.compareTo(stopTime) > 0) {
            throw new BusinessException(ResultEnum.NOT_IN_RESERVATION_TIME);
        }
        // 预约周期最后一天，未到放号时间
        if (workDate.equals(DateTimeHelper.addDays(today, cycle)) && now.compareTo(releaseTime) < 0) {
            throw new BusinessException(ResultEnum.NOT_IN_RESERVATION_TIME);
        }

        if (availableNumber <= 0) {
            throw new BusinessException(ResultEnum.RESERVATION_FULL);
        }
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
    public IPage<OrderInfo> getOrderList(Long userId, OrderInfoQueryParam orderInfoQueryParam, Long current, Long size) {
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderInfo::getUserId, userId);
        lambdaQueryWrapper.eq(StringUtils.hasText(orderInfoQueryParam.getPatientId()), OrderInfo::getPatientId, orderInfoQueryParam.getPatientId());
        lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(orderInfoQueryParam.getOrderStatus()), OrderInfo::getOrderStatus, orderInfoQueryParam.getOrderStatus());
        lambdaQueryWrapper.orderByDesc(OrderInfo::getUpdateTime); // 按更新时间倒序
        return baseMapper.selectPage(new Page<>(current, size), lambdaQueryWrapper);
    }

    @Override
    public Boolean cancelOrder(Long userId, Long orderId) {
        // 查询订单
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderInfo::getUserId, userId);
        lambdaQueryWrapper.eq(OrderInfo::getId, orderId);
        OrderInfo orderInfo = baseMapper.selectOne(lambdaQueryWrapper);
        if (ObjectUtils.isEmpty(orderInfo)) {
            throw new BusinessException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 未支付->取消；支付->退款；其他状态->不可取消
        Integer orderStatus = orderInfo.getOrderStatus();
        if (OrderInfo.ORDER_STATUS_UNPAID.equals(orderStatus)) {
            orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_CLOSED);
        } else if (OrderInfo.ORDER_STATUS_PAID.equals(orderStatus)) {
            orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_REFUNDING);
            // 退款相关逻辑
        } else {
            throw new BusinessException(ResultEnum.ORDER_STATUS_CANNOT_CANCEL);
        }

        // 通知医院
        hospitalSideApiClient.updateOrderInner(orderInfo);
        if (baseMapper.updateById(orderInfo) <= 0) {
            throw new BusinessException(ResultEnum.ORDER_STATUS_UPDATE_FAILED);
        }
        return true;
    }
}




