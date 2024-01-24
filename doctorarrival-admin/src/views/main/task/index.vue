<template>
  <div class="layout-container">
    <div style="margin-top: 10%;">
      <el-button type="primary" @click="visitNotification">发送就诊通知</el-button>
      <el-button type="primary" @click="updateOrderStatus">更新订单状态</el-button>
    </div>
  </div>
</template>

<script setup>
import { visitNotificationApi, updateOrderStatusApi } from "@/api/task";
import { ElMessage } from "element-plus";

function visitNotification() {
  visitNotificationApi().then(() => {
    ElMessage({
      message: "就诊通知将在稍后发送",
      type: "success",
    });
  });
}

function updateOrderStatus() {
  updateOrderStatusApi().then(() => {
    ElMessage({
      message: "订单状态将在稍后更新",
      type: "success",
    });
  });
}
</script>
