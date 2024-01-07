<template>
  <div class="fixed w-full">
    <div class="container flex items-center h-10">
      <NImage src="@/assets/logo.png" preview-disabled class="mr-2" />
      <span class="mr-auto">医来预约挂号统一平台</span>
      <input type="text" v-model="searchContent" placeholder="搜索" class="mr-2" />
      <div>
        <span v-if="isLoggedIn">{{ username }}</span>
        <button v-else @click="login">登录</button>
      </div>
    </div>
  </div>
  <div class="h-10"></div>
</template>

<script setup>
const searchContent = ref('');
const isLoggedIn = ref(false);
const username = ref('');

function login() {
  // 登录逻辑
};
</script>

<style scoped>
</style>