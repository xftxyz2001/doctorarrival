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
          <hospitalsearcher />
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
                <span
                  class="item v-link clickable"
                  v-for="item in hospitalTypeList"
                  :key="item.id"
                  @click="hospitalTypeSelect(item)"
                  :class="hospitalQueryObj.hospitalType === item.id ? 'selected' : ''"
                >
                  {{ item.name }}
                </span>
              </div>
            </div>

            <!-- 地区筛选 -->
            <div class="filter-wrapper">
              <span class="label">省份：</span>
              <div class="condition-wrapper">
                <span
                  class="item v-link clickable"
                  v-for="item in provinceList"
                  :key="item.id"
                  @click="provinceSelect(item)"
                  :class="hospitalQueryObj.provinceCode === item.id ? 'selected' : ''"
                >
                  {{ item.name }}
                </span>
              </div>
            </div>
            <div class="filter-wrapper">
              <span class="label">城市：</span>
              <div class="condition-wrapper">
                <span
                  class="item v-link clickable"
                  v-for="item in cityList"
                  :key="item.id"
                  @click="citySelect(item)"
                  :class="hospitalQueryObj.cityCode === item.id ? 'selected' : ''"
                >
                  {{ item.name }}
                </span>
              </div>
            </div>
            <div class="filter-wrapper">
              <span class="label">地区：</span>
              <div class="condition-wrapper">
                <span
                  class="item v-link clickable"
                  v-for="item in districtList"
                  :key="item.id"
                  @click="districtSelect(item)"
                  :class="hospitalQueryObj.districtCode === item.id ? 'selected' : ''"
                >
                  {{ item.name }}
                </span>
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
                      <span class="iconfont"></span>
                      {{ item.hospitalType }}
                    </div>
                    <div class="icon-wrapper">
                      <span class="iconfont"></span>
                      每天{{ item.bookingRule?.releaseTime }}放号
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
            <span class="item v-link clickable dark">神经内科</span>
            <span class="item v-link clickable dark">消化内科</span>
            <span class="item v-link clickable dark">呼吸内科</span>
            <span class="item v-link clickable dark">内科</span>
            <span class="item v-link clickable dark">神经外科</span>
            <span class="item v-link clickable dark">妇科</span>
            <span class="item v-link clickable dark">产科</span>
            <span class="item v-link clickable dark">儿科</span>
          </div>
        </div>

        <!-- 平台公告 -->
        <div class="space">
          <div class="header-wrapper">
            <div class="title-wrapper">
              <div class="icon-wrapper">
                <span class="iconfont title-icon"></span>
              </div>
              <span class="title">平台公告</span>
            </div>
            <div class="all-wrapper">
              <span>全部</span>
              <span class="iconfont icon"></span>
            </div>
          </div>
          <div class="content-wrapper">
            <div class="notice-wrapper">
              <div class="point"></div>
              <span class="notice v-link clickable dark">关于延长北京大学国际医院放假的通知</span>
            </div>
            <div class="notice-wrapper">
              <div class="point"></div>
              <span class="notice v-link clickable dark">北京中医药大学东方医院部分科室医生门诊医</span>
            </div>
            <div class="notice-wrapper">
              <div class="point"></div>
              <span class="notice v-link clickable dark">武警总医院号源暂停更新通知</span>
            </div>
          </div>
        </div>

        <!-- 停诊公告 -->
        <div class="suspend-notice-list space">
          <div class="header-wrapper">
            <div class="title-wrapper">
              <div class="icon-wrapper">
                <span class="iconfont title-icon"></span>
              </div>
              <span class="title">停诊公告</span>
            </div>
            <div class="all-wrapper">
              <span>全部</span>
              <span class="iconfont icon"></span>
            </div>
          </div>
          <div class="content-wrapper">
            <div class="notice-wrapper">
              <div class="point"></div>
              <span class="notice v-link clickable dark">
                中国人民解放军总医院第六医学中心(原海军总医院)呼吸内科门诊停诊公告
              </span>
            </div>
            <div class="notice-wrapper">
              <div class="point"></div>
              <span class="notice v-link clickable dark">首都医科大学附属北京潞河医院老年医学科门诊停诊公告</span>
            </div>
            <div class="notice-wrapper">
              <div class="point"></div>
              <span class="notice v-link clickable dark">中日友好医院中西医结合心内科门诊停诊公告</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getDictChildrenByDictCode, getDictChildrenByParentId } from "@/api/dict";
import { findHospitalPage } from "@/api/hospital";

const hospitalQueryObj = ref({}); // 医院查询对象

const hospitalTypeList = ref([]); // 医院类型列表
const provinceList = ref([]); // 省份列表
const cityList = ref([]); // 城市列表
const districtList = ref([]); // 地区列表

const hospitalList = ref([]); // 医院列表

// 获取医院类型列表
function getHospitalTypeList() {
  getDictChildrenByDictCode("hospitalType").then(res => {
    hospitalTypeList.value = res;
  });
}
getHospitalTypeList();

// 医院类型选择
function hospitalTypeSelect(item) {
  hospitalQueryObj.value.hospitalType = item.value;
  getHospitalList();
}

// 获取省份列表
function getProvinceList() {
  getDictChildrenByDictCode("AdministrativeDivisions").then(res => {
    provinceList.value = res;
  });
}
getProvinceList();

// 省份选择
function provinceSelect(item) {
  // 省
  hospitalQueryObj.value.provinceCode = item.id;
  // 市
  hospitalQueryObj.value.cityCode = undefined;
  getCityList(item.id);
  // 区
  hospitalQueryObj.value.districtCode = undefined;
  districtList.value = [];
  getHospitalList();
}

// 获取城市列表
function getCityList(provinceCode) {
  getDictChildrenByParentId(provinceCode).then(res => {
    cityList.value = res;
  });
}

// 城市选择
function citySelect(item) {
  // 市
  hospitalQueryObj.value.cityCode = item.id;
  // 区
  hospitalQueryObj.value.districtCode = undefined;
  getDistrictList(item.id);
  getHospitalList();
}

// 获取地区列表
function getDistrictList(cityCode) {
  getDictChildrenByParentId(cityCode).then(res => {
    districtList.value = res;
  });
}

// 地区选择
function districtSelect(item) {
  // 区
  hospitalQueryObj.value.districtCode = item.id;
  getHospitalList();
}

// 获取医院列表
function getHospitalList() {
  findHospitalPage(hospitalQueryObj.value, 1, 10).then(res => {
    hospitalList.value = res.records;
  });
}
getHospitalList();

// 跳转到医院页面
const router = useRouter();
function gotoHospital(hospitalCode) {
  router.push(`/hospital/${hospitalCode}`);
}
</script>
