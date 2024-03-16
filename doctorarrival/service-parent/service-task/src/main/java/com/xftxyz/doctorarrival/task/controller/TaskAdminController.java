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

    @Operation(summary = "发送就诊通知")
    @PostMapping("/visit/notification")
    public void visitNotification() {
        taskService.visitNotification();
    }

    @Operation(summary = "更新订单状态")
    @PostMapping("/update/order/status")
    public void updateOrderStatus() {
        taskService.updateOrderStatus();
    }

}
