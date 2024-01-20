<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <usernavigator />

    <!-- 右侧 内容 -->
    <div class="page-container">
      <vue-utterances repo="xftxyz2001/nwu-graduation-design" theme="github-light" issue-term="pathname" />

      <div style="float: right">
        <a href="https://github.com/khalby786/vue-utterances" target="_blank">Powered by utterances</a>
      </div>
    </div>
  </div>
</template>

<script setup>
import VueUtterances from "vue-utterances";
</script>
