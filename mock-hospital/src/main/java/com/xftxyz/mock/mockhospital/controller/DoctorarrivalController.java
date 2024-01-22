package com.xftxyz.mock.mockhospital.controller;

import com.xftxyz.doctorarrival.result.Result;
import com.xftxyz.doctorarrival.sdk.callback.UpdateOrderRequest;
import com.xftxyz.doctorarrival.sdk.constant.ApiUrls;
import com.xftxyz.doctorarrival.sdk.service.DoctorarrivalService;
import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import com.xftxyz.mock.mockhospital.domain.OrderInfo;
import com.xftxyz.mock.mockhospital.domain.Schedule;
import com.xftxyz.mock.mockhospital.repository.OrderInfoRepository;
import com.xftxyz.mock.mockhospital.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/da")
@RequiredArgsConstructor
public class DoctorarrivalController {

    private final DoctorarrivalService doctorarrivalService;

    private final ScheduleRepository scheduleRepository;
    private final OrderInfoRepository orderInfoRepository;

    @PostMapping(ApiUrls.ORDER)
    public Result submitOrder(@RequestBody EncryptionRequest encryptionRequest) {
        return Result.success(doctorarrivalService.processSubmitOrder(encryptionRequest, (request, response) -> {
            Long id = request.getId();
            String scheduleId = request.getScheduleId();

            List<OrderInfo> query = orderInfoRepository.query(orderInfo -> orderInfo.getId().equals(id));
            if (!query.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("订单已存在");
                return;
            }

            List<Schedule> query1 = scheduleRepository.query(schedule -> schedule.getId().equals(scheduleId));
            if (query1.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("排班不存在");
                return;
            }
            Schedule schedule = query1.get(0);
            if (schedule.getAvailableNumber() <= 0) {
                response.setSuccess(false);
                response.setMessage("预约已满");
                response.setAvailableNumber(schedule.getAvailableNumber());
                return;
            }

            schedule.setAvailableNumber(schedule.getAvailableNumber() - 1);

            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setId(request.getId());
            orderInfo.setDepartmentCode(request.getDepartmentCode());
            orderInfo.setDepartmentName(request.getDepartmentName());
            orderInfo.setDoctorName(request.getDoctorName());
            orderInfo.setScheduleId(scheduleId);
            orderInfo.setReserveDate(request.getReserveDate());
            orderInfo.setPatientName(request.getPatientName());
            orderInfo.setPatientPhone(request.getPatientPhone());
            orderInfo.setAmount(request.getAmount());
            orderInfo.setOrderStatus(request.getOrderStatus());
            orderInfo.setCreateTime(request.getCreateTime());

            orderInfoRepository.save(orderInfo);
            log.info("订单已保存：{}", orderInfo);

            response.setSuccess(true);
            response.setAvailableNumber(schedule.getAvailableNumber());
        }));
    }

    @PostMapping(ApiUrls.UPDATE_ORDER_STATUS)
    public Result updateOrderStatus(@RequestBody EncryptionRequest encryptionRequest) {
        return Result.success(doctorarrivalService.processUpdateOrderStatus(encryptionRequest, (request, response) -> {
            Long id = request.getId();
            Integer status = request.getOrderStatus();
            List<OrderInfo> query = orderInfoRepository.query(orderInfo -> orderInfo.getId().equals(id));
            if (query.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("订单不存在");
                return;
            }
            OrderInfo orderInfo = query.get(0);
            orderInfo.setOrderStatus(status);
            log.info("订单已更新：{}", orderInfo);
            if (UpdateOrderRequest.ORDER_STATUS_CLOSED.equals(status)
                    || UpdateOrderRequest.ORDER_STATUS_REFUNDED.equals(status)) {
                List<Schedule> query1 = scheduleRepository.query(schedule -> schedule.getId().equals(orderInfo.getScheduleId()));
                if (query1.isEmpty()) {
                    response.setSuccess(false);
                    response.setMessage("排班不存在");
                    return;
                }
                Schedule schedule = query1.get(0);
                schedule.setAvailableNumber(schedule.getAvailableNumber() + 1);
                response.setAvailableNumber(schedule.getAvailableNumber());
            }
            response.setSuccess(true);
        }));
    }
}
