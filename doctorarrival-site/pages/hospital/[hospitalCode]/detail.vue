<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <hospitalnavigator />

    <!-- 右侧 内容 -->
    <div class="page-container">
      <div class="hospital-detail">
        <div class="common-header">
          <div class="title-wrapper">
            <span class="hospital-title">{{ hospital.hospitalName }}</span>
            <div class="icon-wrapper">
              <span class="iconfont"></span>
              {{ hospital.hospitalType }}
            </div>
          </div>
        </div>
        <div class="info-wrapper">
          <img :src="hospital.logoData" :alt="hospital.hospitalName" style="width: 80px; height: 80px" />
          <div class="content-wrapper">
            <div></div>
            <div></div>
            <div></div>
            <div>
              <div class="icon-text-wrapper">
                <span class="iconfont prefix-icon"></span>
                <span class="text">
                  <p>{{ hospital.route }}</p>
                </span>
                <span class="iconfont right-icon"></span>
              </div>
            </div>
          </div>
        </div>
        <div class="title mt40">医院介绍</div>
        <div class="detail-content mt40">
          <p>{{ hospital.intro }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { findHospitalByHospitalCode } from "@/api/hospital";

const route = useRoute();
const { hospitalCode } = route.params;

const hospital = ref({});
function initHospital() {
  findHospitalByHospitalCode(hospitalCode).then(res => {
    hospital.value = res;
  });
}
initHospital();
</script>

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
