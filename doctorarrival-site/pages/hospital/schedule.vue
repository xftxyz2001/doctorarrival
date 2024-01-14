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
      <div class="hospital-source-list">
        <!-- 医院名称 / 大科室 / 科室名 -->
        <div class="header-wrapper" style="justify-content: normal">
          <span class="v-link clickable" @click="gotoHospital(hospital.hospitalCode)">{{ hospital.hospitalName }}</span>
          <div class="split"></div>
          <div>{{ department.primaryDepartmentName }}</div>
        </div>
        <div class="title mt20">{{ department.departmentName }}</div>

        <!-- 号源列表 -->
        <div class="mt60">
          <div class="title-wrapper">号源列表</div>
          <div class="calendar-list-wrapper">
            <!-- 日期 号源 -->
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { findHospitalByHospitalCode, getDepartmentByHospitalCodeAndDepartmentCode, getScheduleByHospitalCodeAndDepartmentCode } from '@/api/hospital'

const route = useRoute()
const router = useRouter()
const { hospitalCode, departmentCode } = route.query

const hospital = ref({})
const department = ref({})
const scheduleList = ref([])

// 获取医院信息
function initHospital() {
  findHospitalByHospitalCode(hospitalCode).then(res => {
    hospital.value = res
  })
}
initHospital()

// 回到医院页面
function gotoHospital(hospitalCode) {
  router.push(`/hospital/${hospitalCode}`)
}

// 获取科室信息
function initDepartment() {
  getDepartmentByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode).then(res => {
    department.value = res
  })
}
initDepartment()

// 获取号源信息
function initSchedule() {
  getScheduleByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode).then(res => {
    scheduleList.value = res
  })
}
initSchedule()
</script>

<style scoped>
@import 'assets/css/hospital_personal.css';
@import 'assets/css/hospital.css';
</style>