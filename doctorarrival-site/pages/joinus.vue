<template>
  <div class="page-container" style="display: flex; justify-content: center">
    <div class="personal-patient">
      <div class="header-wrapper">
        <div class="title">加入我们</div>
      </div>

      <div>
        <div class="sub-title">
          <div class="block"></div>
          基本信息
        </div>
        <div class="content-wrapper">
          <el-form :model="formModel" label-width="110px" label-position="left" ref="patient" :rules="validateRules">
            <el-form-item prop="hospitalCode" label="医院编号：">
              <el-input v-model="formModel.hospitalCode" placeholder="请输入医院编号" class="input v-input" />
            </el-form-item>
            <el-form-item prop="hospitalName" label="医院名称：">
              <el-input v-model="formModel.hospitalName" placeholder="请输入医院名称" class="input v-input" />
            </el-form-item>
            <el-form-item prop="apiUrl" label="api基础路径：">
              <el-input v-model="formModel.apiUrl" placeholder="请输入api基础路径" class="input v-input" />
            </el-form-item>
            <el-form-item prop="contactsName" label="联系人：">
              <el-input v-model="formModel.contactsName" placeholder="请输入联系人" class="input v-input" />
            </el-form-item>
            <el-form-item prop="contactsPhone" label="联系人手机：">
              <el-input v-model="formModel.contactsPhone" placeholder="请输入联系人手机" class="input v-input" />
            </el-form-item>
          </el-form>
        </div>

        <div class="sub-title">
          <div class="block"></div>
          详细信息（选填）
        </div>
        <div class="content-wrapper">
          <el-form :model="formModel" label-width="110px" label-position="left" ref="patient" :rules="validateRules">
            <el-form-item prop="hospitalType" label="医院类型：">
              <el-select v-model="formModel.hospitalType" placeholder="请选择医院类型" class="v-select patient-select">
                <el-option
                  v-for="item in hospitalTypeList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="provinceCode" label="省：">
              <el-select
                v-model="formModel.provinceCode"
                placeholder="请选择省"
                class="v-select patient-select"
                @change="provinceSelect"
              >
                <el-option
                  v-for="item in provinceList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="cityCode" label="市：">
              <el-select
                v-model="formModel.cityCode"
                placeholder="请选择市"
                class="v-select patient-select"
                @change="citySelect"
              >
                <el-option v-for="item in cityList" :key="item.id" :label="item.name" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="districtCode" label="区：">
              <el-select v-model="formModel.districtCode" placeholder="请选择区" class="v-select patient-select">
                <el-option
                  v-for="item in districtList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="address" label="详情地址：">
              <el-input v-model="formModel.address" placeholder="请输入详情地址" class="input v-input" />
            </el-form-item>
            <el-form-item prop="logoData" label="医院logo：">
              <el-input v-model="formModel.logoData" placeholder="请输入医院logo（url/base64）" class="input v-input" />
            </el-form-item>
            <el-form-item prop="intro" label="医院简介：">
              <el-input v-model="formModel.intro" placeholder="请输入医院简介" class="input v-input" />
            </el-form-item>
            <el-form-item prop="route" label="坐车路线：">
              <el-input v-model="formModel.route" placeholder="请输入坐车路线" class="input v-input" />
            </el-form-item>
          </el-form>
        </div>
      </div>

      <div class="bottom-wrapper">
        <div class="button-wrapper">
          <div class="v-button" @click="joinButtonClicked">加入</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getDictChildrenByDictCode, getDictChildrenByParentId } from "@/api/dict";
import { join } from "@/api/hospital";

const router = useRouter();

const hospitalTypeList = ref([]); // 医院类型列表
const provinceList = ref([]); // 省份列表
const cityList = ref([]); // 城市列表
const districtList = ref([]); // 地区列表

const defaultFormModel = {
  hospitalCode: "", // 医院编号
  hospitalName: "", // 医院名称
  apiUrl: "", // api基础路径
  contactsName: "", // 联系人
  contactsPhone: "", // 联系人手机

  hospitalType: "", // 医院类型
  provinceCode: "", // 省code
  cityCode: "", // 市code
  districtCode: "", // 区code
  address: "", // 详情地址
  logoData: "", // 医院logo
  intro: "", // 医院简介
  route: "" // 坐车路线
};

const formModel = ref({ ...defaultFormModel });

// 获取医院类型列表
function getHospitalTypeList() {
  getDictChildrenByDictCode("hospitalType").then(res => {
    hospitalTypeList.value = res;
  });
}
getHospitalTypeList();

// 获取省份列表
function getProvinceList() {
  getDictChildrenByDictCode("AdministrativeDivisions").then(res => {
    provinceList.value = res;
  });
}
getProvinceList();

// 省份选择
function provinceSelect(item) {
  // 市
  formModel.value.cityCode = undefined;
  getCityList(item);
  // 区
  formModel.value.districtCode = undefined;
  districtList.value = [];
}

// 获取城市列表
function getCityList(provinceCode) {
  getDictChildrenByParentId(provinceCode).then(res => {
    cityList.value = res;
  });
}

// 城市选择
function citySelect(item) {
  // 区
  formModel.value.districtCode = undefined;
  getDistrictList(item);
}

// 获取地区列表
function getDistrictList(cityCode) {
  getDictChildrenByParentId(cityCode).then(res => {
    districtList.value = res;
  });
}

function joinButtonClicked() {
  ElMessage({
    message: "提交中...",
    type: "info"
  })
  join(formModel.value).then(res => {
    formModel.value = { ...defaultFormModel };

    const blob = new Blob([res.data], { type: res.headers["content-type"] });
    const downloadElement = document.createElement("a");
    const href = window.URL.createObjectURL(blob);
    downloadElement.href = href;
    downloadElement.download = res.headers["content-disposition"].match(/filename="(.*)"/)[1];
    document.body.appendChild(downloadElement);
    downloadElement.click();
    document.body.removeChild(downloadElement);
    window.URL.revokeObjectURL(href);

    ElMessage({
      message: "提交成功，等待审核...",
      type: "success"
    });
  });
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
