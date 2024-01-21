<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <usernavigator />

    <!-- 右侧 内容 -->
    <div class="page-container">
      <div class="personal-order">
        <div class="title">挂号订单</div>

        <!-- 条件查询表单 -->
        <el-form :inline="true">
          <el-form-item label="就诊人：">
            <el-select v-model="searchObj.patientId" placeholder="请选择就诊人" class="v-select patient-select">
              <el-option
                v-for="patient in patientList"
                :key="patient.id"
                :label="patient.name + (patient.certificatesNo ? '【' + patient.certificatesNo + '】' : '')"
                :value="patient.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="订单状态：" style="margin-left: 80px">
            <el-select
              v-model="searchObj.orderStatus"
              placeholder="全部"
              class="v-select patient-select"
              style="width: 200px"
            >
              <el-option
                v-for="item in statusList"
                :key="item.status"
                :label="item.comment"
                :value="item.status"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              type="text"
              class="search-button v-link highlight clickable selected"
              @click="searchButtonClicked"
            >
              查询
            </el-button>
          </el-form-item>
        </el-form>

        <!-- 订单展示表格 -->
        <div class="table-wrapper table">
          <el-table :data="orderList" stripe style="width: 100%">
            <el-table-column label="就诊时间" width="120">
              <template v-slot="scope">
                {{ scope.row.reserveDate }}
                {{ scope.row.reserveTime === 0 ? "上午" : "下午" }}
              </template>
            </el-table-column>
            <el-table-column prop="hospitalName" label="医院" width="100"></el-table-column>
            <el-table-column prop="departmentName" label="科室"></el-table-column>
            <el-table-column prop="doctorName" label="医生"></el-table-column>
            <el-table-column prop="amount" label="医事服务费"></el-table-column>
            <el-table-column prop="patientName" label="就诊人"></el-table-column>
            <el-table-column prop="orderStatus" label="订单状态">
              <template v-slot="scope">
                <span v-if="scope.row.orderStatus == -1" style="color: red">已关闭</span>
                <span v-else-if="scope.row.orderStatus == 0">待支付</span>
                <span v-else-if="scope.row.orderStatus == 1">已支付</span>
                <span v-else-if="scope.row.orderStatus == 2">待退款</span>
                <span v-else-if="scope.row.orderStatus == 3">已退款</span>
                <span v-else-if="scope.row.orderStatus == 4">已完成</span>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template v-slot="scope">
                <el-button type="text" class="v-link highlight clickable selected" @click="gotoOrderDetail(scope.row)">
                  详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <!-- 分页 -->
        <el-pagination
          class="pagination"
          style="justify-content: center"
          layout="prev, pager, next"
          :current-page="orderListPage.current"
          :total="orderListPage.total"
          :page-size="orderListPage.size"
          @current-change="pageChange"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getPatientList } from "@/api/user";
import { getOrderList } from "@/api/order";

const router = useRouter();

const searchObj = ref({
  patientId: "",
  orderStatus: null
});

const patientList = ref([]);

function initPatientList() {
  getPatientList().then(res => {
    patientList.value = res;
    patientList.value.unshift({ id: "", name: "全部", certificatesNo: "" });
  });
}
initPatientList();

// （-1：已关闭，0：待支付，1：已支付，2：待退款，3：已退款，4：已完成）
const statusList = ref([
  { status: null, comment: "全部" },
  { status: -1, comment: "已关闭" },
  { status: 0, comment: "待支付" },
  { status: 1, comment: "已支付" },
  { status: 2, comment: "待退款" },
  { status: 3, comment: "已退款" },
  { status: 4, comment: "已完成" }
]);

const orderListPage = ref({});
const orderList = ref([]);

function getOrderListData(page = 1) {
  getOrderList(searchObj.value, page).then(res => {
    orderListPage.value = res;
    orderList.value = res.records;
  });
}
getOrderListData();

function searchButtonClicked() {
  getOrderListData();
}

function pageChange(page) {
  getOrderListData(page);
}

function gotoOrderDetail(row) {
  console.log(row);
  router.push({
    path: "/user/order/detail",
    query: {
      orderId: row.id
    }
  });
}
</script>

<style scoped>
@import "assets/css/hospital_personal.css";
@import "assets/css/hospital.css";
</style>
