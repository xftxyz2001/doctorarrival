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
const route = useRoute();
const { hospitalCode } = route.params;

const navigation = ref([
  {
    name: "预约挂号",
    path: `/hospital/${hospitalCode}`
  },
  {
    name: "医院详情",
    path: `/hospital/${hospitalCode}/detail`
  },
  {
    name: "预约须知",
    path: `/hospital/${hospitalCode}/notice`
  },
  {
    name: "停诊信息",
    path: `/hospital/${hospitalCode}/info`
  },
  {
    name: "查询/取消",
    path: `/hospital/${hospitalCode}/order`
  }
]);
</script>

<style scoped>
@import "assets/css/hospital_personal.css";
@import "assets/css/hospital.css";
</style>
