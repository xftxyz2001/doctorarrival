package com.xftxyz.doctorarrival.task.controller;

import com.xftxyz.doctorarrival.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/task/manual")
public class TaskAdminController {

    private final TaskService taskService;

    @PostMapping("/visit/notification")
    public void visitNotification() {
        taskService.visitNotification();
    }

    @PostMapping("/update/order/status")
    public void updateOrderStatus() {
        taskService.updateOrderStatus();
    }

}
