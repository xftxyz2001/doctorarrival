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
      <div class="nav-item selected">
        <span class="v-link selected dark" @click="$router.push('/user/patient')">就诊人管理</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="$router.push('/user/order')">挂号订单</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark">意见反馈</span>
      </div>
    </div>

    <!-- 右侧 内容 -->
    <div class="page-container">
      <div class="personal-patient">

      </div>
    </div>
  </div>
</template>

<script setup>

const route = useRoute()
const patientId = route.query.id
const router = useRouter()

</script>

<style scoped>
@import 'assets/css/hospital_personal.css';
@import 'assets/css/hospital.css';
@import 'assets/css/personal.css';

.header-wrapper .title {
  font-size: 16px;
  margin-top: 0;
}

.sub-title {
  margin-top: 0;
}

.bottom-wrapper {
  padding: 0;
  margin: 0;
}

.bottom-wrapper .button-wrapper {
  margin-top: 0;
}
</style>