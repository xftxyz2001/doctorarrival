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
          <div id="search" style="display: block">
            <el-autocomplete class="search-input" :prefix-icon="ElIconSearch" v-model="queryString"
              :fetch-suggestions="searchHospitalForSuggestion" :trigger-on-focus="false" @select="handleSelectedHospital"
              placeholder="点击输入医院名称">
              <template v-slot:suffix>
                <span class="search-btn v-link highlight clickable selected">搜索</span>
              </template>
            </el-autocomplete>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const queryString = ref('')

function searchHospitalForSuggestion(queryString, callback) {
  const hospitals = [
    { value: '医院1', address: '地址1' },
    { value: '医院2', address: '地址2' },
    { value: '医院3', address: '地址3' }]
  callback(hospitals)
}
function handleSelectedHospital(selectedHospital) {
  console.log(selectedHospital);
}
</script>