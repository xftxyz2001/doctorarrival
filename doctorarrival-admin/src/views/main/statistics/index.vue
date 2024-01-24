<template>
  <div class="layout-container">
    <div class="layout-container-form flex space-between">
      <div class="layout-container-form-handle">
        <el-select v-model="selectModule" placeholder="请选择统计模块">
          <el-option v-for="t in chartModules" :key="t.value" :label="t.label" :value="t.value"></el-option>
        </el-select>
      </div>
      <div class="layout-container-form-search">
        <el-select v-model="queryForm.unit">
          <el-option v-for="t in datePickerTypes" :key="t.value" :label="t.label" :value="t.value"></el-option>
        </el-select>
        <!-- https://day.js.org/docs/en/display/format#list-of-all-available-formats -->
        <el-date-picker
          v-model="queryForm.from"
          :type="queryForm.unit"
          placeholder="开始时间"
          value-format="YYYY-MM-DD"
        ></el-date-picker>
        <el-date-picker
          v-model="queryForm.to"
          :type="queryForm.unit"
          placeholder="结束时间"
          value-format="YYYY-MM-DD"
        ></el-date-picker>
        <el-button type="primary" @click="handleSearch">查询</el-button>
      </div>
    </div>
  </div>

  <div class="layout-container-table">
    <Chart :option="activeOption" />
  </div>
</template>

<script setup>
import {
  hospitalStatistics, // count from to
  userStatistics, // phone wx total from to
  orderStatistics // closed closedAmount unpaid unpaidAmount paid paidAmount refunding refundingAmount refunded refundedAmount completed completedAmount total totalAmount from to
} from "@/api/statistics";
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import Chart from "@/components/charts/index.vue";

const chartModules = [
  { label: "医院模块", value: "hospital" },
  { label: "用户模块", value: "user" },
  { label: "订单模块", value: "order" }
];
const selectModule = ref("");

const datePickerTypes = [
  { label: "年", value: "year" },
  { label: "月", value: "month" },
  { label: "日", value: "date" }
  // { label: "周", value: "week" }
];

const queryForm = reactive({
  unit: "date",
  from: "",
  to: ""
});

const activeOption = ref({});

function handleHospitalStatistics() {}

function handleUserStatistics() {}

function handleOrderStatistics() {}

function handleSearch() {
  if (!selectModule.value) {
    ElMessage({
      type: "warning",
      message: "请选择统计模块"
    });
    return;
  }
  if (!queryForm.from) {
    ElMessage({
      type: "warning",
      message: "请选择开始时间"
    });
    return;
  }
  if (!queryForm.to) {
    ElMessage({
      type: "warning",
      message: "请选择结束时间"
    });
    return;
  }
  // 转化为Date
  const from = new Date(queryForm.from);
  const to = new Date(queryForm.to);
  // 判断时间范围
  if (from > to) {
    ElMessage({
      type: "warning",
      message: "开始时间需在结束时间之前"
    });
    return;
  }

  switch (selectModule.value) {
    case "hospital":
      handleHospitalStatistics();
      break;
    case "user":
      handleUserStatistics();
      break;
    case "order":
      handleOrderStatistics();
      break;
  }
}
</script>
