<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <div class="nav left-nav">
      <div class="nav-item">
        <span class="v-link clickable dark" @click="$router.push('/user/info')">用户资料</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="$router.push('/user/realname')">实名认证</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="$router.push('/user/patient')">就诊人管理</span>
      </div>
      <div class="nav-item selected">
        <span class="v-link selected dark" @click="$router.push('/user/order')">挂号订单</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark">意见反馈</span>
      </div>
    </div>

    <!-- 右侧 内容 -->
  </div>
</template>

<script setup></script>

<style scoped>
@import "assets/css/hospital_personal.css";
@import "assets/css/hospital.css";

.info-wrapper {
  padding-left: 0;
  padding-top: 0;
}

.content-wrapper {
  color: #333;
  font-size: 14px;
  padding-bottom: 0;
}

.bottom-wrapper {
  width: 100%;
}

.button-wrapper {
  margin: 0;
}

.el-form-item {
  margin-bottom: 5px;
}

.bottom-wrapper .button-wrapper {
  margin-top: 0;
}
</style>
