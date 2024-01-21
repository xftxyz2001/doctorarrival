<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <usernavigator />

    <!-- 右侧 内容 -->
    <div class="page-container">
      <div class="personal-patient">
        <div class="title" style="margin-top: 0px; font-size: 16px">就诊人详情</div>

        <div>
          <div class="sub-title">
            <div class="block"></div>
            就诊人信息
          </div>

          <!-- 信息展示区 -->
          <div class="content-wrapper">
            <el-form :model="patient" label-width="110px" label-position="left">
              <el-form-item label="姓名：">
                <div class="">
                  <span>{{ patient.name }}</span>
                </div>
              </el-form-item>
              <el-form-item label="证件类型：">
                <div class="">
                  <span>{{ patient.certificatesTypeName }}</span>
                </div>
              </el-form-item>
              <el-form-item label="证件号码：">
                <div class="">
                  <span>{{ patient.certificatesNo }}</span>
                </div>
              </el-form-item>
              <el-form-item label="性别：">
                <div class="">
                  <span>{{ genderString }}</span>
                </div>
              </el-form-item>
              <el-form-item label="出生日期：">
                <div class="">
                  <span>{{ patient.birthday }}</span>
                </div>
              </el-form-item>
              <el-form-item label="手机号码：">
                <div class="">
                  <span>{{ patient.phone }}</span>
                </div>
              </el-form-item>
              <el-form-item label="婚姻状况：">
                <div class="">
                  <span>{{ marryString }}</span>
                </div>
              </el-form-item>

              <br />
              <el-form-item>
                <el-button class="v-button" type="danger" @click="remove">删除就诊人</el-button>
                <el-button class="v-button" type="primary" @click="edit">修改就诊人</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getPatientDetail, removePatient } from "@/api/user";

const route = useRoute();
const router = useRouter();
const patientId = route.query.id;

const patient = ref({});

function initPatient() {
  getPatientDetail(patientId).then(res => {
    patient.value = res;
  });
}
initPatient();

// 删除就诊人
function remove() {
  ElMessageBox.confirm("确定删除该就诊人吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  })
    .then(() => {
      removePatient(patientId).then(() => {
        ElMessage({
          type: "success",
          message: "删除成功"
        });
        router.push("/user/patient");
      });
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "已取消删除"
      });
    });
}

// 修改就诊人
function edit() {
  router.push({
    path: "/user/patient/edit",
    query: {
      id: patientId
    }
  });
}

const genderString = computed(() => {
  return (patient.value.gender = patient.value.gender == 1 ? "男" : patient.value.gender == 0 ? "女" : "保密");
});

const marryString = computed(() => {
  return (patient.value.marry = patient.value.marry == 1 ? "已婚" : patient.value.marry == 0 ? "未婚" : "保密");
});
</script>

<style scoped>
@import "assets/css/hospital_personal.css";
@import "assets/css/hospital.css";
@import "assets/css/personal.css";

.info-wrapper {
  padding-left: 0;
  padding-top: 0;
}

.content-wrapper {
  color: #333;
  font-size: 14px;
  padding-bottom: 0;
}

.el-form-item {
  margin-bottom: 5px;
}

.bottom-wrapper {
  width: 100%;
}

.button-wrapper {
  margin: 0;
}

.bottom-wrapper .button-wrapper {
  margin-top: 0;
}
</style>
