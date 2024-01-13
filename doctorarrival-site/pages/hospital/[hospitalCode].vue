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

  </div>
</template>

<script setup>
import 'assets/css/hospital_personal.css'
import 'assets/css/hospital.css'

// 路由
function gotoHospital() {
  ElMessage({
    message: '预约挂号页面',
    type: 'warning'
  })
}

function gotoHospitalDetail() {
  ElMessage({
    message: '医院详情页面',
    type: 'warning'
  })
}

function gotoHospitalNotice() {
  ElMessage({
    message: '预约须知页面',
    type: 'warning'
  })
}

</script>