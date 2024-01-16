<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <hospitalnavigator />

    <!-- 右侧 内容 -->
    <div class="page-container">
      <div class="hospital-order">
        <div class="header-wrapper">
          <div class="title mt20">确认挂号信息</div>
          <div>
            <div class="sub-title">
              <div class="block"></div>
              选择就诊人：
            </div>

            <!-- 就诊人选择 -->
            <div class="patient-wrapper">
              <div>
                <div class="v-card clickable item">
                  <div
                    class="inline"
                    v-for="patient in patientListFix"
                    :key="patient.id"
                    @click="selectPatient(patient)"
                    style="margin-right: 10px"
                  >
                    <div class="item-wrapper" :class="activePatient.id == patient.id ? 'selected' : ''">
                      <div>
                        <div class="item-title">{{ patient.name }}</div>
                        <div>{{ patient.certificatesType }}</div>
                        <div>{{ patient.certificatesNo }}</div>
                      </div>
                      <img src="assets/images/checked.png" class="checked" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="item space add-patient v-card clickable">
                <div class="">
                  <div class="item-add-wrapper" @click="addPatient">+ 添加就诊人</div>
                </div>
              </div>
              <div class="el-loading-mask" style="display: none">
                <div class="el-loading-spinner">
                  <svg viewBox="25 25 50 50" class="circular">
                    <circle cx="50" cy="50" r="20" fill="none" class="path"></circle>
                  </svg>
                </div>
              </div>
            </div>

            <!-- 就诊卡展示 -->
            <div class="sub-title" v-if="showInfo">
              <div class="block"></div>
              选择就诊卡：
              <span class="card-tips">
                <span class="iconfont"></span>
                如您持社保卡就诊，请务必选择医保预约挂号，以保证正常医保报销
              </span>
            </div>
            <el-card class="patient-card" shadow="always" v-if="showInfo">
              <template v-slot:header>
                <div class="clearfix">
                  <div>
                    <span class="name">
                      {{ activePatient.name }}
                      {{ activePatient.certificatesNo }} {{ activePatient.certificatesType }}
                    </span>
                  </div>
                </div>
              </template>
              <div class="card SELF_PAY_CARD">
                <div class="info">
                  <span class="type">{{ activePatient.insure == 0 ? "自费" : "医保" }}</span>
                  <span class="card-no">{{ activePatient.certificatesNo }}</span>
                  <span class="card-view">{{ activePatient.certificatesType }}</span>
                </div>
                <span class="operate"></span>
              </div>
              <div class="card">
                <div class="text bind-card"></div>
              </div>
            </el-card>

            <!-- 挂号信息展示 -->
            <div class="sub-title">
              <div class="block"></div>
              挂号信息
            </div>
            <div class="content-wrapper">
              <el-form ref="form">
                <el-form-item label="就诊日期：">
                  <div class="content">
                    <span>
                      {{ schedule.workDate }} {{ "schedule.param.dayOfWeek" }}
                      {{ schedule.workTime == 0 ? "上午" : "下午" }}
                    </span>
                  </div>
                </el-form-item>
                <el-form-item label="就诊医院：">
                  <div class="content">
                    <span>{{ schedule.hospitalCode }}</span>
                  </div>
                </el-form-item>
                <el-form-item label="就诊科室：">
                  <div class="content">
                    <span>{{ schedule.departmentCode }}</span>
                  </div>
                </el-form-item>
                <el-form-item label="医生姓名：">
                  <div class="content">
                    <span>{{ schedule.doctorName }}</span>
                  </div>
                </el-form-item>
                <el-form-item label="医生职称：">
                  <div class="content">
                    <span>{{ schedule.doctorTitle }}</span>
                  </div>
                </el-form-item>
                <el-form-item label="医生专长：">
                  <div class="content">
                    <span>{{ schedule.skill }}</span>
                  </div>
                </el-form-item>
                <el-form-item label="医事服务费：">
                  <div class="content">
                    <div class="fee">{{ schedule.amount }}元</div>
                  </div>
                </el-form-item>
              </el-form>
            </div>

            <!-- 用户信息 -->
            <div v-show="showInfo">
              <div class="sub-title">
                <div class="block"></div>
                用户信息
              </div>
              <div class="content-wrapper">
                <el-form ref="form">
                  <el-form-item class="form-item" label="就诊人手机号：">
                    {{ activePatient.phone }}
                  </el-form-item>
                  <!-- ... -->
                </el-form>
              </div>
            </div>
            <!-- 确认提交 -->
            <div class="bottom-wrapper">
              <div class="button-wrapper">
                <div class="v-button" @click="submitOrder">
                  {{ submitButtonText }}
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
import { getDictChildrenByDictCode } from "@/api/dict";
import { getScheduleById } from "@/api/hospital";
import { getPatientList } from "@/api/user";

const route = useRoute();
const router = useRouter();
const { scheduleId } = route.query;
const schedule = ref({});

const certificatesTypeList = ref([]);

const patientList = ref([]);
const activePatient = ref({});

const submitButtonText = ref("提交");

// 初始化排班信息
function initSchedule() {
  getScheduleById(scheduleId).then(res => {
    schedule.value = res;
  });
}
initSchedule();

// 初始化证件类型
function initCertificatesTypeList() {
  getDictChildrenByDictCode("CertificatesType").then(res => {
    certificatesTypeList.value = res;
    // 修正id
    certificatesTypeList.value.forEach(item => {
      item.id = item.id % 1000000;
    });
  });
}
initCertificatesTypeList();

// 初始化就诊人列表
function initPatientList() {
  getPatientList().then(res => {
    patientList.value = res;
    // activePatient.value = res[0];
  });
}
initPatientList();

// 修正证件类型
const patientListFix = computed(() => {
  return patientList.value.map(item => {
    const certificatesType = certificatesTypeList.value.find(type => type.id === item.certificatesType);
    return {
      ...item,
      certificatesType: certificatesType ? certificatesType.value : ""
    };
  });
});

const showInfo = computed(() => {
  return Object.keys(activePatient.value).length > 0;
});

// 选择就诊人
function selectPatient(patient) {
  activePatient.value = patient;
}

// 添加就诊人
function addPatient() {
  router.push("/user/patient/edit");
}

// 提交订单
function submitOrder() {
  if (!activePatient.value.id) {
    ElMessage({
      message: "请选择就诊人",
      type: "warning"
    });
    return;
  }
  ElMessage({
    message: "正在提交...",
    type: "success"
  });
  submitButtonText.value = "提交中...";

  setTimeout(() => {
    submitButtonText.value = "提交";
  }, 2000);
}
</script>

<style scoped>
@import "assets/css/hospital_personal.css";
@import "assets/css/hospital.css";

.hospital-order .header-wrapper {
  display: -webkit-box;
  display: -ms-flexbox;
  display: block !important;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}
.hospital-order .sub-title {
  letter-spacing: 1px;
  color: #999;
  margin-top: 60px;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}
.hospital-order .content-wrapper .content {
  color: #333;
}
.el-form-item {
  margin-bottom: 5px;
}
.hospital-order .content-wrapper {
  margin-left: 140px;
  margin-top: 20px;
}
</style>
