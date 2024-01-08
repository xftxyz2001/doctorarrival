<template>
  <div class="app-container">
    <div id="main">
      <!-- 公共头 -->
      <myheader />
      <div class="main-container">
        <el-scrollbar class="page-component__scroll">
          <!-- 内容区域 -->
          <slot />
        </el-scrollbar>
      </div>
      <!-- 公共底 -->
      <myfooter />
    </div>
  </div>
</template>
