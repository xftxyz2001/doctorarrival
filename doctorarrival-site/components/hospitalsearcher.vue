<template>
  <client-only>
    <el-autocomplete class="search-input" :prefix-icon="ElIconSearch" v-model="queryString" value-key="hospitalName"
      :fetch-suggestions="searchHospitalForSuggestion" @select="handleSelectedHospital" placeholder="点击输入医院名称">
      <template v-slot:suffix>
        <span class="search-btn v-link highlight clickable selected" @click="searchButtonClick">搜索</span>
      </template>
    </el-autocomplete>
  </client-only>
</template>

<script setup>
import { findHospitalByHospitalName } from '@/api/hospital'
const queryString = ref('')
const router = useRouter()

// 自动补全
function searchHospitalForSuggestion(queryString, callback) {
  if (!queryString) {
    return
  }
  findHospitalByHospitalName(queryString).then(res => {
    callback(res)
  })
}

// 选择医院->跳转到医院页面
function handleSelectedHospital(selectedHospital) {
  const { hospitalCode } = selectedHospital
  router.push(`/hospital/${hospitalCode}`)
}

// 点击搜索按钮
function searchButtonClick() {
  if (!queryString.value) {
    return
  }
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
</script>