<template>
  <div class="footer-container">
    <div class="wrapper">
      <div>
        <span class="record">京ICP备00000000号</span>
        <span class="phone">电话挂号000-00000000</span>
      </div>
      <div class="right">
        <span class="v-link clickable" @click="go('https://github.com/xftxyz2001')">我的主页</span>
        <span class="v-link clickable" @click="go('https://github.com/xftxyz2001/nwu-graduation-design')">
          项目首页
        </span>
        <span class="v-link clickable" @click="go('https://github.com/xftxyz2001/nwu-graduation-design/issues/new')">
          问题反馈
        </span>
        <span class="v-link clickable" @click="$router.push('/joinus')">加入我们</span>
      </div>
    </div>
  </div>
</template>

<script setup>
function go(url) {
  if (window) {
    open(url, "_blank");
  }
}
</script>
