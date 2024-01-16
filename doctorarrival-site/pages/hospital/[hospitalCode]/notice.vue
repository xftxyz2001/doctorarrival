<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <div class="nav left-nav">
      <div class="nav-item selected">
        <span class="v-link selected dark" @click="gotoHospital">预约挂号</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="gotoHospitalDetail">医院详情</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="gotoHospitalNotice">预约须知</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark"> 停诊信息 </span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark"> 查询/取消 </span>
      </div>
    </div>

    <!-- 右侧 内容 -->
    <div class="page-container"></div>
  </div>
</template>

<script setup></script>

<style scoped>
@import "assets/css/hospital_personal.css";
@import "assets/css/hospital.css";

.hospital-detail .info-wrapper {
  width: 100%;
  padding-left: 0;
  padding-top: 0;
  margin-top: 0;
  flex-direction: inherit;
}

.hospital-detail .info-wrapper .text {
  font-size: 14px;
}

.hospital-detail .content-wrapper p {
  text-indent: 0;
}
</style>
