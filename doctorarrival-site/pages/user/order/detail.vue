<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <usernavigator />

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
