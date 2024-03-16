package com.xftxyz.doctorarrival.task.controller;

import com.xftxyz.doctorarrival.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "定时任务")
@RequestMapping("/admin/task/manual")
public class TaskAdminController {

    private final TaskService taskService;

    @Operation(summary = "发送就诊通知",
            description = "此接口用于手动触发发送就诊通知的定时任务。一旦调用此接口，服务端将立即执行发送就诊通知的任务逻辑，向待就诊患者发送相应的就诊提醒消息。")
    @PostMapping("/visit/notification")
    public void visitNotification() {
        taskService.visitNotification();
    }

    @Operation(summary = "更新订单状态",
            description = "此接口用于手动触发更新订单状态的定时任务。当调用此接口时，服务端会立即执行该任务逻辑，根据预定的业务规则检查并更新系统中所有订单的状态，例如自动将即将到期的订单状态变更为待确认或其他状态。")
    @PostMapping("/update/order/status")
    public void updateOrderStatus() {
        taskService.updateOrderStatus();
    }

}
