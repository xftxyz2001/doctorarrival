<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <div class="nav left-nav">
      <div class="nav-item">
        <span class="v-link clickable dark" @click="$router.push('/user/info')">用户资料</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="$router.push('/user/realname')">实名认证</span>
      </div>
      <div class="nav-item selected">
        <span class="v-link selected dark" @click="$router.push('/user/patient')">就诊人管理</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="$router.push('/user/order')">挂号订单</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark">意见反馈</span>
      </div>
    </div>

    <!-- 右侧 内容 -->
    <div class="page-container">
      <div class="personal-patient">
        <div class="header-wrapper">
          <div class="title">就诊人管理</div>
        </div>

        <div class="content-wrapper">
          <!-- 展示就诊人 -->
          <el-card class="patient-card" shadow="always" v-for="patient in patientListFix" :key="patient.id">
            <template v-slot:header>
              <div class="clearfix">
                <div>
                  <span class="name">{{ patient.name }}</span>
                  <span>{{ patient.certificatesNo }}
                    {{ patient.certificatesType }}</span>
                  <div class="detail" @click="gotoPatientDetail(patient.id)">
                    查看详情 <span class="iconfont"></span>
                  </div>
                </div>
              </div>
            </template>
            <div class="card SELF_PAY_CARD">
              <div class="info">
                <span class="type">{{ patient.insured == 0 ? '自费' : '医保' }}</span>
                <span class="card-no">{{ patient.certificatesNo }}</span>
                <span class="card-view">{{ patient.certificatesType }}</span>
              </div>
              <span class="operate"></span>
            </div>
            <div class="card">
              <div class="text bind-card"></div>
            </div>
          </el-card>

          <!-- 添加就诊人 -->
          <div class="item-add-wrapper v-card clickable" @click="addPatient">
            <div class="">
              <div>+ 添加就诊人</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getDictChildrenByDictCode } from '@/api/dict';
import { getPatientList } from '@/api/user';

const router = useRouter();

const certificatesTypeList = ref([])
const patientList = ref([])

// 初始化证件类型
function initCertificatesTypeList() {
  getDictChildrenByDictCode('CertificatesType').then(res => {
    certificatesTypeList.value = res
    // 修正id
    certificatesTypeList.value.forEach(item => {
      item.id = item.id % 1000000
    })
  })
}
initCertificatesTypeList()

// 初始化就诊人列表
function initPatientList() {
  getPatientList().then(res => {
    patientList.value = res
  })
}
initPatientList()

// 修正证件类型
const patientListFix = computed(() => {
  return patientList.value.map(item => {
    const certificatesType = certificatesTypeList.value.find(type => type.id === item.certificatesType)
    return {
      ...item,
      certificatesType: certificatesType ? certificatesType.value : ''
    }
  })
})

// 查看就诊人详情
function gotoPatientDetail(id) {
  router.push({
    path: '/user/patient/detail',
    query: {
      id
    }
  })
}

function addPatient() {
  router.push({
    path: '/user/patient/edit'
  })
}
</script>

<style scoped>
@import 'assets/css/hospital_personal.css';
@import 'assets/css/hospital.css';
@import 'assets/css/personal.css';

.header-wrapper .title {
  font-size: 16px;
  margin-top: 0;
}

.content-wrapper {
  margin-left: 0;
}

.patient-card .el-card__header .detail {
  font-size: 14px;
}
</style>