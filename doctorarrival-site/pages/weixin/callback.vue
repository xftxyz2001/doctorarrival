<template>
  <nuxt-layout name="empty">欢迎，{{ nickName }}。正在跳转...</nuxt-layout>
</template>

<script setup>
definePageMeta({
  layout: false
});

const route = useRoute();
const { nickName, token } = route.query;
useToken().value = { nickName, token };
// if (window) {
//   postMessage({ nickName, token }, location.origin);
// }
useRouter().push("/");
</script>
