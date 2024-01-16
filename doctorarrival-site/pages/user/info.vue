<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <div class="nav left-nav">
      <div class="nav-item selected">
        <span class="v-link selected dark" @click="$router.push('/user/info')">用户资料</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="$router.push('/user/realname')">实名认证</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="$router.push('/user/patient')">就诊人管理</span>
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
      <div>
        <div class="header-wrapper">
          <div class="title">用户资料</div>
        </div>

        <div class="content-wrapper">
          <div class="context-container">
            <div>
              <el-form :model="userInfo" label-width="110px" label-position="right">
                <el-form-item prop="id" label="uid：" class="form-normal">
                  {{ userInfo.id }}
                </el-form-item>
                <el-form-item prop="nickName" label="昵称：">
                  <span v-if="!editing.nickName">{{ userInfo.nickName }}</span>
                  <el-button class="ml20" @click="toggleEdit('nickName')" v-if="!editing.nickName">修改</el-button>

                  <div v-if="editing.nickName" style="display: flex; align-items: center">
                    <el-input v-if="editing.nickName" v-model="userInfo.nickName" />
                    <el-button class="ml20" @click="save('nickName')" v-if="editing.nickName">√</el-button>
                  </div>
                </el-form-item>
                <el-form-item prop="phone" label="手机号：">
                  <span v-if="!editing.phone">{{ userInfo.phone }}</span>
                  <el-button class="ml20" @click="toggleEdit('phone')" v-if="!editing.phone">修改</el-button>

                  <div v-if="editing.phone" style="display: flex; align-items: center">
                    <el-input v-model="userInfo.phone" />
                    <el-button class="ml20" @click="getVerificationCode">{{ getVerificationCodeButtonText }}</el-button>

                    <el-input v-model="verificationCode" placeholder="验证码" />
                    <el-button class="ml20" @click="save('phone')">√</el-button>
                  </div>
                </el-form-item>
                <el-form-item prop="openid" label="微信openid：">
                  {{ userInfo.openid }}
                </el-form-item>
              </el-form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { sendVerificationCode } from "@/api/sms";
import { getUserInfoDetail, updatePhone, updateNickname } from "@/api/user";

const userInfo = ref({});
const verificationCode = ref("");

const getVerificationCodeButtonText = ref("获取验证码");
let clearSmsTime = null; // 倒计时定时任务引用

const editing = ref({
  nickName: false,
  phone: false
});

function initUserInfo() {
  getUserInfoDetail().then(res => {
    userInfo.value = res;
  });
}
initUserInfo();

// 检查手机号合法性
function checkPhone(phone) {
  if (!phone) {
    ElMessage({
      message: "请输入手机号",
      type: "warning"
    });
    return false;
  }
  if (!/^1[3-9]\d{9}$/.test(phone)) {
    ElMessage({
      message: "手机号格式不正确",
      type: "warning"
    });
    return false;
  }
  return true;
}

// 倒计时
function countDown() {
  if (clearSmsTime) {
    clearInterval(clearSmsTime);
  }
  getVerificationCodeButtonText.value = 60; // 下次发送倒计时
  clearSmsTime = setInterval(() => {
    getVerificationCodeButtonText.value--;
    if (getVerificationCodeButtonText.value <= 0) {
      clearInterval(clearSmsTime);
      getVerificationCodeButtonText.value = "获取验证码";
    }
  }, 1000);
}

// 发送验证码
function getVerificationCode() {
  const phoneNumber = userInfo.value.phone;
  checkPhone(phoneNumber);

  if (getVerificationCodeButtonText.value != "获取验证码") {
    return;
  }
  countDown(); // 倒计时

  sendVerificationCode(phoneNumber);
  ElMessage({
    type: "success",
    message: "验证码已发送至" + phoneNumber
  });
}

function toggleEdit(key) {
  editing.value[key] = !editing.value[key];
}

function save(key) {
  if (key === "nickName") {
    updateNickname(userInfo.value.nickName).then(res => {
      if (res) {
        initUserInfo();
        // userInfo.value = res
        ElMessage({
          type: "success",
          message: "修改成功"
        });
        // location.reload()
      }
    });
  } else if (key === "phone") {
    const params = {
      phoneNumber: userInfo.value.phone,
      verificationCode: verificationCode.value
    };
    updatePhone(params).then(res => {
      if (res) {
        initUserInfo();
        // userInfo.value = res
        ElMessage({
          type: "success",
          message: "修改成功"
        });
        // location.reload()
      }
    });
  }
  editing.value[key] = false;
}
</script>

<style scoped>
@import "assets/css/hospital_personal.css";
@import "assets/css/hospital.css";
@import "assets/css/personal.css";

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

.page-container .title {
  letter-spacing: 1px;
  font-weight: 700;
  color: #333;
  font-size: 16px;
  margin-top: 0;
  margin-bottom: 20px;
}

.page-container .tips {
  width: 100%;
  padding-left: 0;
}

.page-container .form-wrapper {
  padding-left: 92px;
  width: 580px;
}

.form-normal {
  height: 40px;
}

.bottom-wrapper {
  width: 100%;
  padding: 0;
  margin-top: 0;
}
</style>
