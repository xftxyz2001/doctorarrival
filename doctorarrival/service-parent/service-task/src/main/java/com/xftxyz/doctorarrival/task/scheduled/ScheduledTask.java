package com.xftxyz.doctorarrival.task.scheduled;

import com.xftxyz.doctorarrival.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTask {

    private final TaskService taskService;

    // 生成cron表达式 https://cron.qqe2.com/

    /**
     * 就诊通知
     */
    @Scheduled(cron = "0 0 8 * * ?") // 每天8点执行一次
    public void visitNotification() {
        taskService.visitNotification();
    }

    /**
     * 更新订单状态
     */
    @Scheduled(cron = "0 * * * * ?") // 每分钟执行一次
    public void updateOrderStatus() {
        taskService.updateOrderStatus();
    }

    /**
     * TODO 更新医院、科室、排班信息
     */

}
