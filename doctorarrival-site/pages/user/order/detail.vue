<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <usernavigator />

    <!-- 右侧 内容 -->
    <div class="page-container">
      <div class="order-detail">
        <div class="title">挂号详情</div>

        <!-- 状态条 -->
        <div class="status-bar">
          <div class="left-wrapper">
            <div class="status-wrapper BOOKING_SUCCESS">
              <span class="iconfont"></span>
              <!-- （-1：已关闭，0：待支付，1：已支付，2：待退款，3：已退款，4：已完成） -->
              {{
                orderInfo.orderStatus === -1
                  ? "已关闭"
                  : orderInfo.orderStatus === 0
                    ? "待支付"
                    : orderInfo.orderStatus === 1
                      ? "已支付"
                      : orderInfo.orderStatus === 2
                        ? "待退款"
                        : orderInfo.orderStatus === 3
                          ? "已退款"
                          : orderInfo.orderStatus === 4
                            ? "已完成"
                            : ""
              }}
            </div>
          </div>
          <div class="right-wrapper">
            <img src="assets/images/code_order_detail.png" class="code-img" />
            <div class="content-wrapper">
              <div>
                微信
                <span class="iconfont"></span>
                关注 “医来”微信公众号”
              </div>
              <div class="watch-wrapper">快速挂号，轻松就医</div>
            </div>
          </div>
        </div>

        <!-- 信息展示 -->
        <div class="info-wrapper">
          <div class="title-wrapper">
            <div class="block"></div>
            <div>挂号信息</div>
          </div>
          <div class="info-form">
            <el-form ref="form" :model="form">
              <el-form-item label="就诊人信息：">
                <div class="content">
                  <span>{{ orderInfo.patientName }}</span>
                </div>
              </el-form-item>
              <el-form-item label="就诊日期：">
                <div class="content">
                  <span>{{ orderInfo.reserveDate }} {{ orderInfo.reserveTime == 0 ? "上午" : "下午" }}</span>
                </div>
              </el-form-item>
              <el-form-item label="就诊医院：">
                <div class="content">
                  <span>{{ orderInfo.hospitalName }}</span>
                </div>
              </el-form-item>
              <el-form-item label="就诊科室：">
                <div class="content">
                  <span>{{ orderInfo.departmentName }}</span>
                </div>
              </el-form-item>
              <el-form-item label="医生信息：">
                <div class="content">
                  <span>{{ orderInfo.doctorTitle }} | {{ orderInfo.doctorName }}</span>
                </div>
              </el-form-item>
              <el-form-item label="医事服务费：">
                <div class="content">
                  <div class="fee">{{ orderInfo.amount / 100 }}元</div>
                </div>
              </el-form-item>
              <el-form-item label="挂号单号：">
                <div class="content">
                  <span>{{ orderInfo.id }}</span>
                </div>
              </el-form-item>
              <el-form-item label="挂号时间：">
                <div class="content">
                  <span>{{ orderInfo.createTime }}</span>
                </div>
              </el-form-item>
            </el-form>
          </div>
        </div>

        <!-- 注意事项：后面考虑展示医院侧返回的事项 -->
        <div class="rule-wrapper mt40">
          <div class="rule-title">注意事项</div>
          <div>
            1、请确认就诊人信息是否准确，若填写错误将无法取号就诊，损失由本人承担；
            <br />
            <span style="color: red">
              2、【取号】就诊当天需携带预约挂号所使用的有效身份证件到院取号，未取号视为爽约，该号不退不换；
            </span>
            <br />
            3、【退号】在就诊前一天可在线退号，逾期将不可办理退号退费；
            <br />
            4、本平台支持自费患者使用身份证预约，同时支持医保患者使用社保卡在平台预约挂号。请于就诊当日，携带预约挂号所使用的有效身份证件到院取号；
            <br />
            5、请注意医保患者在住院期间不能使用社保卡在门诊取号。
          </div>
        </div>

        <!-- 操作按钮 -->
        <div class="bottom-wrapper mt60" v-if="orderInfo.orderStatus == 0 || orderInfo.orderStatus == 1">
          <div class="button-wrapper">
            <div class="v-button white" @click="cancelOrderButtonClicked">取消预约</div>
          </div>
          <div class="button-wrapper ml20" v-if="orderInfo.orderStatus == 0">
            <div class="v-button" @click="pay">支付</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 微信支付弹出框 -->
    <el-dialog
      v-model="dialogPayVisible"
      style="text-align: left"
      :append-to-body="true"
      width="500px"
      @close="closeDialog"
    >
      <div class="container">
        <div class="operate-view" style="height: 350px">
          <div class="wrapper wechat">
            <div>
              <!-- <qriously :value="payQrCodeUrl" :size="220" /> -->
              <img :src="payQrCodeUrl" style="width: 220px; height: 220px" alt="二维码" />
              <div style="text-align: center; line-height: 25px; margin-bottom: 40px">
                请使用微信扫一扫
                <br />
                扫描二维码支付
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { getOrderDetail, cancelOrder } from "@/api/order";
import qrcode from "qrcode";

const route = useRoute();
const { orderId } = route.query;

const orderInfo = ref({});

const dialogPayVisible = ref(false);
const payQrCodeUrl = ref("");

function getOrderDetailData() {
  getOrderDetail(orderId).then(res => {
    orderInfo.value = res;
  });
}
getOrderDetailData();

function cancelOrderButtonClicked() {
  ElMessageBox({
    type: "warning",
    title: "提示",
    message: "确定取消预约吗？",
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    callback: action => {
      if (action === "confirm") {
        cancelOrder(orderId).then(res => {
          if (res) {
            getOrderDetailData();
          }
        });
      }
    }
  });
}

function pay() {
  // 获取支付二维码
  qrcode.toDataURL("https://www.baidu.com").then(url => {
    console.log(url);
    payQrCodeUrl.value = url;
  });

  dialogPayVisible.value = true;
}

function closeDialog() {
  dialogPayVisible.value = false;
  ElMessage({
    type: "info",
    message: "支付已取消"
  });
}
</script>

<style scoped>
@import "assets/css/hospital_personal.css";
@import "assets/css/hospital.css";

.info-wrapper {
  padding-left: 0;
  padding-top: 0;
}

.content-wrapper {
  color: #333;
  font-size: 14px;
  padding-bottom: 0;
}

.bottom-wrapper {
  width: 100%;
}

.button-wrapper {
  margin: 0;
}

.el-form-item {
  margin-bottom: 5px;
}

.bottom-wrapper .button-wrapper {
  margin-top: 0;
}
</style>
