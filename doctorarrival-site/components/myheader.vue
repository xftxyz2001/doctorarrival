<template>
  <div class="header-container">
    <div class="wrapper">
      <!-- logo -->
      <div class="left-wrapper v-link selected">
        <img style="width: 50px; height: 50px;" src="assets/images/logo.png" />
        <span class="text">“医来” 预约挂号统一平台</span>
      </div>

      <!-- 搜索框 -->
      <div class="search-wrapper">
        <div class="hospital-search animation-show">
          <div id="search" style="display: block;width: 100%;">
            <client-only>
              <el-autocomplete class="search-input" :prefix-icon="ElIconSearch" v-model="queryString"
                value-key="hospitalName" :fetch-suggestions="searchHospitalForSuggestion" @select="handleSelectedHospital"
                placeholder="点击输入医院名称">
                <template v-slot:suffix>
                  <span class="search-btn v-link highlight clickable selected" @click="searchButtonClick">搜索</span>
                </template>
              </el-autocomplete>
            </client-only>
          </div>
        </div>
      </div>

      <!-- 右侧 -->
      <div class="right-wrapper">
        <span class="v-link clickable">帮助中心</span>

        <!-- 未登录状态 -->
        <span v-if="!nickName" class="v-link clickable" @click="showLoginDialog">登录/注册</span>

        <!-- 登录状态 -->

      </div>
    </div>

    <!-- 登录弹出层 -->
    <el-dialog v-model="dialogUserFormVisible" style="text-align: left" top="50px" :append-to-body="true" width="960px"
      @close="closeLoginDialog">
      <div class="container">

      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { findHospitalByHospitalName } from '@/api/hospital'

const queryString = ref('')
const dialogUserFormVisible = ref(false) // 登录弹出层
const nickName = ref('') // 昵称

// 自动补全
function searchHospitalForSuggestion(queryString, callback) {
  findHospitalByHospitalName(queryString).then(res => {
    callback(res)
  })
}

// 选择医院
function handleSelectedHospital(selectedHospital) {
  console.log(selectedHospital);
}

// 点击搜索按钮
function searchButtonClick() {
  findHospitalByHospitalName(queryString.value).then(res => {
    if (res.length === 0) {
      ElMessage({
        message: '没有找到医院，请重新输入',
        type: 'error'
      })
    } else if (res.length === 1) {
      handleSelectedHospital(res[0])
    } else {
      ElMessage({
        message: '找到多个医院，请在下拉框中选择',
        type: 'warning'
      })
    }
  })
}

// 点击登录/注册
function showLoginDialog() {
  dialogUserFormVisible.value = true
}

// 关闭登录弹出层
function closeLoginDialog() {
  dialogUserFormVisible.value = false
}
</script>