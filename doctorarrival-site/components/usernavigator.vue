<template>
  <div class="nav left-nav">
    <template v-for="item in navigation" :key="item">
      <div class="nav-item" :class="{ selected: item.path === $route.path }">
        <span
          class="v-link dark"
          :class="{ selected: item.path === $route.path, clickable: item.path !== $route.path }"
          @click="$router.push(item.path)"
        >
          {{ item.name }}
        </span>
      </div>
    </template>
  </div>
</template>

<script setup>
const navigation = ref([
  {
    name: "用户资料",
    path: "/user/info"
  },
  {
    name: "实名认证",
    path: "/user/realname"
  },
  {
    name: "就诊人管理",
    path: "/user/patient"
  },
  {
    name: "挂号订单",
    path: "/user/order"
  },
  {
    name: "意见反馈",
    path: "/user/feedback"
  }
]);
</script>

<style scoped>
@import "assets/css/hospital_personal.css";
@import "assets/css/hospital.css";
</style>
