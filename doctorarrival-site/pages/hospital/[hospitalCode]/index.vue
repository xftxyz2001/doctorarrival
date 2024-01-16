<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <div class="nav left-nav">
      <div class="nav-item selected">
        <span class="v-link selected dark" @click="gotoHospital">预约挂号</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="gotoHospitalDetail">医院详情</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="gotoHospitalNotice">预约须知</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark"> 停诊信息 </span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark"> 查询/取消 </span>
      </div>
    </div>

    <!-- 右侧 内容 -->
    <div class="page-container" :key="componentKey">
      <div class="hospital-home">
        <!-- 医院名称/类型 -->
        <div class="common-header">
          <div class="title-wrapper">
            <span class="hospital-title">{{ hospital.hospitalName }}</span>
            <div class="icon-wrapper"><span class="iconfont"></span>{{ hospital.hospitalType }}</div>
          </div>
        </div>

        <!-- 医院信息/挂号规则 -->
        <div class="info-wrapper">
          <img class="hospital-img" :src="hospital.logoData" :alt="hospital.hospitalName" />
          <div class="content-wrapper">
            <div>挂号规则</div>
            <div class="line">
              <div>
                <span class="label">预约周期：</span><span>{{ hospitalBookingRule.cycle }}天</span>
              </div>
              <div class="space">
                <span class="label">放号时间：</span><span>{{ hospitalBookingRule.releaseTime }}</span>
              </div>
              <div class="space">
                <span class="label">停挂时间：</span><span>{{ hospitalBookingRule.stopTime }}</span>
              </div>
            </div>
            <div class="line">
              <span class="label">退号时间：</span>
              <span v-if="hospitalBookingRule.quitDay == -1"
                >就诊前一工作日{{ hospitalBookingRule.quitTime }}前取消</span
              >
              <span v-if="hospitalBookingRule.quitDay == 0">就诊前当天{{ hospitalBookingRule.quitTime }}前取消</span>
            </div>
            <div style="margin-top: 20px">医院预约规则</div>
            <div class="rule-wrapper">
              <ol>
                <li v-for="item in hospitalBookingRule.rule" :key="item">{{ item }}</li>
              </ol>
            </div>
          </div>
        </div>

        <!-- 科室 -->
        <div class="title select-title">选择科室</div>
        <div class="select-dept-wrapper">
          <!-- 大科室导航 -->
          <div class="department-wrapper">
            <div class="hospital-department">
              <div class="dept-list-wrapper el-scrollbar" style="height: 100%">
                <div class="dept-list el-scrollbar__wrap" style="margin-bottom: -17px; margin-right: -17px">
                  <div class="el-scrollbar__view">
                    <!-- 展示区 -->
                    <div
                      class="sub-item"
                      v-for="primaryDepartment in departmentList"
                      :key="primaryDepartment.departmentCode"
                      :class="
                        primaryDepartment.departmentCode === activePrimaryDepartment.departmentCode ? 'selected' : ''
                      "
                      @click="scrollto(primaryDepartment)"
                    >
                      {{ primaryDepartment.departmentName }}
                    </div>
                  </div>
                </div>
                <div class="el-scrollbar__bar is-horizontal">
                  <div class="el-scrollbar__thumb" style="transform: translateX(0%)"></div>
                </div>
                <div class="el-scrollbar__bar is-vertical">
                  <div class="el-scrollbar__thumb" style="transform: translateY(0%); height: 91.4761%"></div>
                </div>
              </div>
            </div>
          </div>

          <!-- 科室展示 -->
          <div class="sub-dept-container">
            <!-- 展示区 -->
            <div
              v-for="primaryDepartment in departmentList"
              :key="primaryDepartment.departmentCode"
              class="sub-dept-wrapper"
              :id="primaryDepartment.departmentCode"
              :class="primaryDepartment.departmentCode === activePrimaryDepartment.departmentCode ? 'selected' : ''"
            >
              <div class="sub-title">
                <div class="block selected"></div>
                {{ primaryDepartment.departmentName }}
              </div>
              <div class="sub-item-wrapper">
                <div
                  v-for="childrenDepartment in primaryDepartment.children"
                  :key="childrenDepartment.departmentCode"
                  class="sub-item"
                  @click="gotoSchedule(childrenDepartment)"
                >
                  <span class="v-link clickable">{{ childrenDepartment.departmentName }} </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { findHospitalByHospitalCode, getDepartmentByHospitalCode } from "@/api/hospital";

const componentKey = ref(0);

const route = useRoute();
const router = useRouter();
const hospitalCode = route.params.hospitalCode;

const hospital = ref({});
const hospitalBookingRule = ref({});

const departmentList = ref([]);
const activePrimaryDepartment = ref({});

// 获取医院信息
function initHospital() {
  findHospitalByHospitalCode(hospitalCode).then(res => {
    hospital.value = res;
    hospitalBookingRule.value = res.bookingRule;
    componentKey.value++; // 重新渲染页面
  });
}
initHospital();

// 获取科室信息
function initDepartment() {
  getDepartmentByHospitalCode(hospitalCode).then(res => {
    departmentList.value = res;
    activePrimaryDepartment.value = res[0];
  });
}
initDepartment();

// 滚动到指定位置
function scrollto(primaryDepartment) {
  const el = document.getElementById(primaryDepartment.departmentCode);
  el.scrollIntoView({
    behavior: "smooth"
  });
  activePrimaryDepartment.value = primaryDepartment;
}

// 前往排班页面
function gotoSchedule(department) {
  router.push({
    path: `/hospital/schedule`,
    query: {
      hospitalCode: hospitalCode,
      departmentCode: department.departmentCode
    }
  });
}

// 路由
function gotoHospital() {
  ElMessage({
    message: "预约挂号页面",
    type: "warning"
  });
}

function gotoHospitalDetail() {
  ElMessage({
    message: "医院详情页面",
    type: "warning"
  });
}

function gotoHospitalNotice() {
  ElMessage({
    message: "预约须知页面",
    type: "warning"
  });
}
</script>

<style scoped>
@import "assets/css/hospital_personal.css";
@import "assets/css/hospital.css";
</style>
