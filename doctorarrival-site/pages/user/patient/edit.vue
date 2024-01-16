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
          <div class="title">{{ patientId ? '修改' : '添加' }}就诊人</div>
        </div>

        <div>
          <div class="sub-title">
            <div class="block"></div>
            就诊人信息
          </div>
          <div class="content-wrapper">
            <el-form :model="formModel" label-width="110px" label-position="left" ref="patient" :rules="validateRules">
              <el-form-item prop="name" label="姓名：">
                <el-input v-model="formModel.name" placeholder="请输入真实姓名全称" class="input v-input" />
              </el-form-item>
              <el-form-item prop="certificatesType" label="证件类型：">
                <el-select v-model="formModel.certificatesType" placeholder="请选择证件类型" class="v-select patient-select">
                  <el-option v-for="item in certificatesTypeList" :key="item.id" :label="item.value" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item prop="certificatesNo" label="证件号码：">
                <el-input v-model="formModel.certificatesNo" placeholder="请输入证件号码" class="input v-input" />
              </el-form-item>
              <el-form-item prop="gender" label="性别：">
                <el-radio-group v-model="formModel.gender">
                  <el-radio :label="1">男</el-radio>
                  <el-radio :label="0">女</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item prop="birthday" label="出生日期：">
                <el-date-picker v-model="formModel.birthday" class="v-date-picker" type="date" placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item prop="phone" label="手机号码：">
                <el-input v-model="formModel.phone" placeholder="请输入手机号码" maxlength="11" class="input v-input" />
              </el-form-item>
            </el-form>
          </div>

          <div class="sub-title">
            <div class="block"></div>
            建档信息（完善后部分医院首次就诊不排队建档）
          </div>
          <div class="content-wrapper">
            <el-form :model="formModel" label-width="110px" label-position="left" ref="patient" :rules="validateRules">
              <el-form-item prop="marry" label="婚姻状况：">
                <el-radio-group v-model="formModel.marry">
                  <el-radio :label="0">未婚</el-radio>
                  <el-radio :label="1">已婚</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item prop="insured" label="自费/医保：">
                <el-radio-group v-model="formModel.insured">
                  <el-radio :label="0">自费</el-radio>
                  <el-radio :label="1">医保</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item prop="cardNo" label="医保卡号：" v-show="formModel.insured === 1">
                <el-input v-model="formModel.cardNo" placeholder="请输入医保卡号" class="input v-input" />
              </el-form-item>
              <!-- <el-form-item prop="addressSelected" label="当前住址：">
                <el-cascader ref="selectedShow" v-model="formModel.addressSelected" class="v-address"
                  :props="props"></el-cascader>
              </el-form-item>
              <el-form-item prop="address" label="详细地址：">
                <el-input v-model="formModel.address" placeholder="应公安机关要求，请填写现真实住址" class="input v-input" />
              </el-form-item> -->
            </el-form>
          </div>

          <div class="sub-title">
            <div class="block"></div>
            联系人信息（选填）
          </div>
          <div class="content-wrapper">
            <el-form :model="formModel" label-width="110px" label-position="left">
              <el-form-item prop="contactsName" label="姓名：">
                <el-input v-model="formModel.contactsName" placeholder="请输入联系人姓名全称" class="input v-input" />
              </el-form-item>
              <!-- <el-form-item prop="contactsCertificatesType" label="证件类型：">
                <el-select v-model="formModel.contactsCertificatesType" placeholder="请选择证件类型"
                  class="v-select patient-select">
                  <el-option v-for="item in certificatesTypeList" :key="item.value" :label="item.name"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item prop="contactsCertificatesNo" label="证件号码：">
                <el-input v-model="formModel.contactsCertificatesNo" contactsCertificatesNo="请输入联系人证件号码"
                  class="input v-input" />
              </el-form-item> -->
              <el-form-item prop="contactsPhone" label="手机号码：">
                <el-input v-model="formModel.contactsPhone" placeholder="请输入联系人手机号码" class="input v-input" />
              </el-form-item>
            </el-form>
          </div>

        </div>

        <div class="bottom-wrapper">
          <div class="button-wrapper">
            <div class="v-button" @click="saveOrUpdate">{{ submitButtonText }}</div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import { getDictChildrenByDictCode } from '@/api/dict'
import { getPatientDetail, addPatient, updatePatient } from '@/api/user'

const route = useRoute()
const patientId = route.query.id
const router = useRouter()

const certificatesTypeList = ref([])
// 表单
const formModel = ref({
  name: '',
  certificatesType: '',
  certificatesNo: '',
  gender: '',
  birthday: '',
  phone: '',

  marry: '', // 
  insured: '',
  cardNo: '',
  contactsName: '',
  contactsPhone: '',
})
const submitButtonText = ref('保存')

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

function initPatient() {
  if (patientId) {
    getPatientDetail(patientId).then(res => {
      formModel.value = res
    })
  }
}
initPatient()

function saveOrUpdate() {
  if (patientId) {
    updatePatient(formModel.value).then(() => {
      ElMessage({
        type: 'success',
        message: '更新成功',
      })
      router.push('/user/patient')
    })
  } else {
    addPatient(formModel.value).then(() => {
      ElMessage({
        type: 'success',
        message: '添加成功',
      })
      router.push('/user/patient')
    })
  }
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

.sub-title {
  margin-top: 0;
}

.bottom-wrapper {
  padding: 0;
  margin: 0;
}

.bottom-wrapper .button-wrapper {
  margin-top: 0;
}
</style>