<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <div class="nav left-nav">
      <div class="nav-item">
        <span class="v-link clickable dark" @click="$router.push('/user/info')">用户资料</span>
      </div>
      <div class="nav-item selected">
        <span class="v-link selected dark" @click="$router.push('/user/realname')">实名认证</span>
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
    <div class="page-container" :key="componentKey">
      <div>
        <div class="title">实名认证</div>
        <!-- 认证状态 -->
        <div class="status-bar">
          <div class="status-wrapper">
            <span class="iconfont"></span>
            {{ authStatusString }}
          </div>
        </div>

        <!-- 认证提示 -->
        <div class="tips">
          <span class="iconfont"></span>
          完成实名认证后才能添加就诊人，正常进行挂号，为了不影响后续步骤，建议提前实名认证。
        </div>

        <!-- 未认证/认证失败 -->
        <div class="form-wrapper" v-if="userInfo.authStatus === 0 || userInfo.authStatus === -1">
          <div>
            <!-- 认证信息表单 -->
            <el-form :model="formModel" label-width="110px" label-position="left">
              <el-form-item prop="name" label="姓名：" class="form-normal">
                <div class="name-input">
                  <el-input v-model="formModel.name" placeholder="请输入联系人姓名全称" class="input v-input" />
                </div>
              </el-form-item>

              <el-form-item prop="certificatesType" label="证件类型：">
                <el-select
                  v-model="formModel.certificatesType"
                  placeholder="请选择证件类型"
                  class="v-select patient-select"
                >
                  <el-option v-for="item in certificatesTypeList" :key="item.id" :label="item.value" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item prop="certificatesNo" label="证件号码：">
                <el-input v-model="formModel.certificatesNo" placeholder="请输入联系人证件号码" class="input v-input" />
              </el-form-item>

              <el-form-item prop="name" label="上传证件：">
                <div class="upload-wrapper">
                  <!-- 上传文件 -->
                  <div class="avatar-uploader">
                    <el-upload class="avatar-uploader" :show-file-list="false" :http-request="handleCacheHeadersUpload">
                      <div class="upload-inner-wrapper">
                        <img v-if="formModel.certificatesUrl" :src="certificatesFilePreview" class="avatar" />
                        <el-icon class="avatar-uploader-icon"><el-icon-plus /></el-icon>
                        <div v-if="!formModel.certificatesUrl" class="text">上传证件合照</div>
                      </div>
                    </el-upload>
                  </div>
                  <!-- 示例 -->
                  <img src="assets/images/auth_example.png" class="example" />
                </div>
              </el-form-item>
            </el-form>

            <!-- 提交按钮 -->
            <div class="bottom-wrapper">
              <div class="button-wrapper">
                <div class="v-button" @click="saveUserAuth">
                  {{ submitButtonText }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 认证成功 -->
        <div class="context-container" v-if="userInfo.authStatus === 2">
          <div>
            <el-form :model="userInfo" label-width="110px" label-position="right">
              <el-form-item prop="name" label="姓名：" class="form-normal">
                <div class="name-input">
                  {{ userInfo.name }}
                </div>
              </el-form-item>
              <el-form-item prop="certificatesType" label="证件类型：">
                {{ certificatesTypeString }}
              </el-form-item>
              <el-form-item prop="certificatesNo" label="证件号码：">
                {{ userInfo.certificatesNo }}
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getDictChildrenByDictCode } from "@/api/dict";
import { uploadCertificates } from "@/api/oss";
import { getUserInfoDetail, saveRealName } from "@/api/user";

const componentKey = ref(0);

const userInfo = ref({});

const certificatesTypeList = ref([]);

// 证件照片预览
const certificatesFilePreview = ref("");

// 表单
const formModel = ref({
  name: null, // 姓名
  certificatesType: null, // 证件类型
  certificatesNo: null, // 证件号码
  certificatesUrl: null // 证件照片
});

// 提交按钮
const submitButtonText = ref("提交");

function initUserInfo() {
  getUserInfoDetail().then(res => {
    userInfo.value = res;

    // 如果是认证失败
    if (userInfo.value.authStatus === -1) {
      // 回填表单
      formModel.value.name = userInfo.value.name;
      formModel.value.certificatesType = userInfo.value.certificatesType;
      formModel.value.certificatesNo = userInfo.value.certificatesNo;
      // formModel.value.certificatesUrl = userInfo.value.certificatesUrl
      // 按钮
      submitButtonText.value = "更新";
    }
    // 重新渲染
    componentKey.value++;
  });
}
initUserInfo();

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

// 上传证件照片
function handleCacheHeadersUpload(file) {
  const realFile = file.file;

  // 上传
  const formData = new FormData();
  formData.append("file", file.file);
  uploadCertificates(formData).then(res => {
    formModel.value.certificatesUrl = res;
  });

  // 预览
  const reader = new FileReader();
  reader.onload = e => {
    certificatesFilePreview.value = e.target.result;
  };
  reader.readAsDataURL(realFile);
}

// 保存认证信息
function saveUserAuth() {
  // 校验
  const { name, certificatesType, certificatesNo, certificatesUrl } = formModel.value;
  if (!name) {
    ElMessage({
      message: "请输入联系人姓名全称",
      type: "warning"
    });
    return;
  }
  if (!certificatesType) {
    ElMessage({
      message: "请选择证件类型",
      type: "warning"
    });
    return;
  }
  if (!certificatesNo) {
    ElMessage({
      message: "请输入联系人证件号码",
      type: "warning"
    });
    return;
  }
  if (!certificatesUrl) {
    ElMessage({
      message: "请上传证件合照",
      type: "warning"
    });
    return;
  }

  // 提交
  submitButtonText.value = "正在提交...";
  const params = {
    name,
    certificatesType,
    certificatesNo,
    certificatesUrl
  };
  saveRealName(params).then(res => {
    // submitButtonText.value = '提交'
    initUserInfo();
  });
}

// 计算属性
const authStatusString = computed(() => {
  switch (userInfo.value.authStatus) {
    case 0:
      return "未认证";
    case 1:
      return "认证中";
    case 2:
      return "认证成功";
    case -1:
      return "认证失败";
    default:
      return "未认证";
  }
});

const certificatesTypeString = computed(() => {
  const certificatesType = certificatesTypeList.value.find(item => item.id === userInfo.value.certificatesType);
  return certificatesType ? certificatesType.value : "未知";
});
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
