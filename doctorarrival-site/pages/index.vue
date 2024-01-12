<template>
  <div class="home page-component">
    <!-- banner -->
    <el-carousel indicator-position="outside">
      <el-carousel-item v-for="item in 1" :key="item">
        <img src="assets/images/web-banner1.png" alt="暂无图片" />
      </el-carousel-item>
    </el-carousel>

    <!-- 搜索框 -->
    <div class="search-container">
      <div class="search-wrapper">
        <div class="hospital-search">
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

    <!-- 下半部分 -->
    <div class="bottom">
      <!-- 主体（左） -->
      <div class="left">
        <!-- 筛选器部分 -->
        <div class="home-filter-wrapper">
          <div class="title">医院</div>

          <!-- 筛选器们 -->
          <div>
            <!-- 类型筛选 -->
            <div class="filter-wrapper">
              <span class="label">等级：</span>
              <div class="condition-wrapper">
                <span class="item v-link clickable" v-for="item in hospitalTypeList" :key="item.id"
                  @click="hospitalTypeSelect(item)" :class="hospitalQueryObj.hospitalType === item.id ? 'selected' : ''">
                  {{ item.value }}</span>
              </div>
            </div>

            <!-- 地区筛选 -->
            <div class="filter-wrapper">
              <span class="label">省份：</span>
              <div class="condition-wrapper">
                <span class="item v-link clickable" v-for="item in provinceList" :key="item.id"
                  @click="provinceSelect(item)" :class="hospitalQueryObj.provinceCode === item.id ? 'selected' : ''">
                  {{ item.value }}</span>
              </div>
            </div>
            <div class="filter-wrapper">
              <span class="label">城市：</span>
              <div class="condition-wrapper">
                <span class="item v-link clickable" v-for="item in cityList" :key="item.id" @click="citySelect(item)"
                  :class="hospitalQueryObj.cityCode === item.id ? 'selected' : ''">
                  {{ item.value }}</span>
              </div>
            </div>
            <div class="filter-wrapper">
              <span class="label">地区：</span>
              <div class="condition-wrapper">
                <span class="item v-link clickable" v-for="item in districtList" :key="item.id"
                  @click="districtSelect(item)" :class="hospitalQueryObj.districtCode === item.id ? 'selected' : ''">
                  {{ item.value }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 展示列表 -->
        <div class="v-scroll-list hospital-list">
          <div class="v-card clickable list-item" v-for="item in hospitalList" :key="item.id">
            <div class="">
              <div class="hospital-list-item hos-item" index="0" @click="gotoHospital(item.hospitalCode)">
                <div class="wrapper">
                  <div class="hospital-title">{{ item.hospitalName }}</div>
                  <div class="bottom-container">
                    <div class="icon-wrapper">
                      <span class="iconfont"></span>{{ item.hospitalType }}
                    </div>
                    <div class="icon-wrapper">
                      <span class="iconfont"></span>每天{{ item.bookingRule?.releaseTime }}放号
                    </div>
                  </div>
                </div>
                <img :src="item.logoData" :alt="item.hospitalName" class="hospital-img" />
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 侧边（右）！！！假数据 -->
      <div class="right">
        <!-- 常见科室 -->
        <div class="common-dept">
          <div class="header-wrapper">
            <div class="title">常见科室</div>
            <div class="all-wrapper">
              <span>全部</span>
              <span class="iconfont icon"></span>
            </div>
          </div>
          <div class="content-wrapper">
            <span class="item v-link clickable dark">神经内科 </span>
            <span class="item v-link clickable dark">消化内科 </span>
            <span class="item v-link clickable dark">呼吸内科 </span>
            <span class="item v-link clickable dark">内科 </span>
            <span class="item v-link clickable dark">神经外科 </span>
            <span class="item v-link clickable dark">妇科 </span>
            <span class="item v-link clickable dark">产科 </span>
            <span class="item v-link clickable dark">儿科 </span>
          </div>
        </div>

        <!-- TODO: 平台公告 -->

        <!-- TODO: 停诊公告 -->

      </div>
    </div>
  </div>
</template>

<script setup>
import { findHospitalByHospitalName, findHospitalPage } from '@/api/hospital'
import { getDictChildrenByDictCode, getDictChildrenByParentId } from '@/api/dict'

const queryString = ref('') // 搜索框输入的内容

const hospitalQueryObj = ref({}) // 医院查询对象

const hospitalTypeList = ref([]) // 医院类型列表
const provinceList = ref([]) // 省份列表
const cityList = ref([]) // 城市列表
const districtList = ref([]) // 地区列表

const hospitalList = ref([]) // 医院列表

// 自动补全
function searchHospitalForSuggestion(queryString, callback) {
  findHospitalByHospitalName(queryString).then(res => {
    callback(res)
  })
}

// 选择医院
function handleSelectedHospital(selectedHospital) {
  gotoHospital(selectedHospital.hospitalCode)
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

// 获取医院类型列表
function getHospitalTypeList() {
  getDictChildrenByDictCode('hospitalType').then(res => {
    hospitalTypeList.value = res
  })
}
getHospitalTypeList()

// 医院类型选择
function hospitalTypeSelect(item) {
  hospitalQueryObj.value.hospitalType = item.id
}

// 获取省份列表
function getProvinceList() {
  getDictChildrenByDictCode('AdministrativeDivisions').then(res => {
    provinceList.value = res
  })
}
getProvinceList()

// 省份选择
function provinceSelect(item) {
  // 省
  hospitalQueryObj.value.provinceCode = item.id
  // 市
  hospitalQueryObj.value.cityCode = undefined
  getCityList(item.id)
  // 区
  hospitalQueryObj.value.districtCode = undefined
  districtList.value = []
  getHospitalList()
}

// 获取城市列表
function getCityList(provinceCode) {
  getDictChildrenByParentId(provinceCode).then(res => {
    cityList.value = res
  })
}

// 城市选择
function citySelect(item) {
  // 市
  hospitalQueryObj.value.cityCode = item.id
  // 区
  hospitalQueryObj.value.districtCode = undefined
  getDistrictList(item.id)
  getHospitalList()
}

// 获取地区列表
function getDistrictList(cityCode) {
  getDictChildrenByParentId(cityCode).then(res => {
    districtList.value = res
  })
}

// 地区选择
function districtSelect(item) {
  // 区
  hospitalQueryObj.value.districtCode = item.id
  getHospitalList()
}

// 获取医院列表
function getHospitalList() {
  findHospitalPage(hospitalQueryObj.value, 1, 10).then(res => {
    hospitalList.value = res.records
  })
}
getHospitalList()

// 跳转到医院页面
function gotoHospital(hospitalCode) {
  console.log("前往" + hospitalCode);
}
</script>