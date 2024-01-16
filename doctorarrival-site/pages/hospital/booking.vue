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
    <div class="page-container">
      <div class="hospital-order">
        <div class="header-wrapper">
          <div class="title mt20">确认挂号信息</div>
          <div>
            <div class="sub-title">
              <div class="block"></div>
              选择就诊人：
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const route = useRoute();
const { scheduleId } = route.query;
</script>

<style scoped>
@import "assets/css/hospital_personal.css";
@import "assets/css/hospital.css";
</style>
