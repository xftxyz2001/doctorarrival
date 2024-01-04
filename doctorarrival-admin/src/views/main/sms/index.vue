<template>
  <div>
    <h1>消息管理</h1>
  </div>

  <!-- 按钮：返回首页 -->
  <router-link to="/">
    <el-button>返回首页</el-button>
  </router-link>
</template>

<script setup>
import { ElMessage } from 'element-plus';

ElMessage({
  message: '该功能暂未开放',
  type: 'warning'
})
</script>
