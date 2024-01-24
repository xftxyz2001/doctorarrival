<template>
  <div class="layout-container">
    <div class="layout-container-form flex space-between">
      <div class="layout-container-form-handle">
        <el-select v-model="selectModule" placeholder="请选择统计模块" @change="somethingChange">
          <el-option v-for="t in chartModules" :key="t.value" :label="t.label" :value="t.value"></el-option>
        </el-select>
      </div>
      <div class="layout-container-form-search">
        <el-select v-model="queryForm.unit" @change="dateUnitChange">
          <el-option v-for="t in datePickerTypes" :key="t.value" :label="t.label" :value="t.value"></el-option>
        </el-select>
        <!-- https://day.js.org/docs/en/display/format#list-of-all-available-formats -->
        <el-date-picker
          v-model="queryForm.from"
          :type="queryForm.unit"
          :disabled-date="disabledDate"
          @change="somethingChange"
          placeholder="开始时间"
          value-format="YYYY-MM-DD"
        ></el-date-picker>
        <el-date-picker
          v-model="queryForm.to"
          :type="queryForm.unit"
          :disabled-date="disabledDate"
          @change="somethingChange"
          placeholder="结束时间"
          value-format="YYYY-MM-DD"
        ></el-date-picker>
        <el-button type="primary" @click="handleSearch">查询</el-button>
      </div>
    </div>

    <div class="layout-container-table">
      <Chart :option="activeOption" />
    </div>
  </div>
</template>

<script setup>
import { hospitalStatistics, userStatistics, orderStatistics } from "@/api/statistics";
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import Chart from "@/components/charts/index.vue";

const chartModules = [
  { label: "医院模块", value: "hospital" },
  { label: "用户模块", value: "user" },
  { label: "订单模块（数目）", value: "order" },
  { label: "订单模块（金额）", value: "orderAmount" }
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

// 禁用未来时间
function disabledDate(time) {
  return time.getTime() > Date.now();
}

// 日期单位改变，忽略小的单位
function dateUnitChange(unit) {
  const fromDate = new Date(queryForm.from);
  const toDate = new Date(queryForm.to);
  if (unit === "year") {
    queryForm.from = fromDate.getFullYear() + "-01-01";
    queryForm.to = toDate.getFullYear() + "-01-01";
  } else if (unit === "month") {
    let fromMonth = fromDate.getMonth() + 1;
    let toMonth = toDate.getMonth() + 1;
    fromMonth = fromMonth < 10 ? "0" + fromMonth : fromMonth;
    toMonth = toMonth < 10 ? "0" + toMonth : toMonth;
    queryForm.from = fromDate.getFullYear() + "-" + fromMonth + "-01";
    queryForm.to = toDate.getFullYear() + "-" + toMonth + "-01";
  } else if (unit === "date") {
    queryForm.from = fromDate.toISOString().split("T")[0];
    queryForm.to = toDate.toISOString().split("T")[0];
  }
}

function check(warning = false) {
  if (!selectModule.value) {
    if (warning)
      ElMessage({
        type: "warning",
        message: "请选择统计模块"
      });
    return false;
  }
  if (!queryForm.from) {
    if (warning)
      ElMessage({
        type: "warning",
        message: "请选择开始时间"
      });
    return false;
  }
  if (!queryForm.to) {
    if (warning)
      ElMessage({
        type: "warning",
        message: "请选择结束时间"
      });
    return false;
  }
  // 转化为Date
  const from = new Date(queryForm.from);
  const to = new Date(queryForm.to);
  // 判断时间范围
  if (from > to) {
    if (warning)
      ElMessage({
        type: "warning",
        message: "开始时间需在结束时间之前"
      });
    return false;
  }

  return true;
}

const activeOption = ref({});

// 生成请求参数
function generateParams() {
  const params = [];
  let currentDate = new Date(queryForm.from);
  const endDate = new Date(queryForm.to);

  while (currentDate <= endDate) {
    const from = currentDate.toISOString().split("T")[0];
    if (queryForm.unit === "date") {
      currentDate.setDate(currentDate.getDate() + 1);
    } else if (queryForm.unit === "month") {
      currentDate.setMonth(currentDate.getMonth() + 1);
    } else if (queryForm.unit === "year") {
      currentDate.setFullYear(currentDate.getFullYear() + 1);
    }
    const to = currentDate.toISOString().split("T")[0];
    params.push({ from, to });
  }

  return params;
}

function handleHospitalStatistics() {
  // 生成请求参数
  const params = generateParams();
  // 发送请求
  const promises = params.map(param => hospitalStatistics(param));
  // 处理结果
  Promise.all(promises).then(results => {
    const xAxisData = [];
    const seriesData = [];
    for (const res of results) {
      xAxisData.push(res.from);
      seriesData.push(res.count);
    }
    activeOption.value = {
      title: {
        text: "医院统计"
      },
      tooltip: {},
      legend: {
        data: ["新增医院数量"]
      },
      xAxis: {
        data: xAxisData
      },
      yAxis: {},
      series: [
        {
          name: "新增医院数量",
          type: "bar",
          data: seriesData
        }
      ]
    };
  });
}

function handleUserStatistics() {
  // 生成请求参数
  const params = generateParams();
  // 发送请求
  const promises = params.map(param => userStatistics(param));
  // 处理结果
  Promise.all(promises).then(results => {
    const xAxisData = [];
    const seriesDataTotal = [];
    const seriesDataPhone = [];
    const seriesDataWx = [];
    for (const res of results) {
      xAxisData.push(res.from);
      seriesDataTotal.push(res.total);
      seriesDataPhone.push(res.phone);
      seriesDataWx.push(res.wx);
    }
    const legendData = ["新增手机用户", "新增微信用户", "新增用户数量"];
    activeOption.value = {
      title: {
        text: "用户统计"
      },
      tooltip: {},
      legend: {
        data: legendData
      },
      xAxis: {
        data: xAxisData
      },
      yAxis: {},
      series: [
        {
          name: legendData[0],
          type: "bar",
          data: seriesDataPhone
        },
        {
          name: legendData[1],
          type: "bar",
          data: seriesDataWx
        },
        {
          name: legendData[2],
          type: "bar",
          data: seriesDataTotal
        }
      ]
    };
  });
}

function handleOrderStatistics(amount = false) {
  // 生成请求参数
  const params = generateParams();
  // 发送请求
  const promises = params.map(param => orderStatistics(param));
  // 处理结果
  Promise.all(promises).then(results => {
    const xAxisData = [];
    const seriesDataClosed = [];
    const seriesDataUnpaid = [];
    const seriesDataPaid = [];
    const seriesDataRefunding = [];
    const seriesDataRefunded = [];
    const seriesDataCompleted = [];
    const seriesDataTotal = [];

    for (const res of results) {
      xAxisData.push(res.from);
      seriesDataClosed.push(amount ? res.closedAmount : res.closed);
      seriesDataUnpaid.push(amount ? res.unpaidAmount : res.unpaid);
      seriesDataPaid.push(amount ? res.paidAmount : res.paid);
      seriesDataRefunding.push(amount ? res.refundingAmount : res.refunding);
      seriesDataRefunded.push(amount ? res.refundedAmount : res.refunded);
      seriesDataCompleted.push(amount ? res.completedAmount : res.completed);
      seriesDataTotal.push(amount ? res.totalAmount : res.total);
    }

    const lagendData = amount
      ? [
          "已关闭订单金额",
          "未支付订单金额",
          "已支付订单金额",
          "退款中订单金额",
          "已退款订单金额",
          "已完成订单金额",
          "订单总金额"
        ]
      : ["已关闭订单数", "未支付订单数", "已支付订单数", "退款中订单数", "已退款订单数", "已完成订单数", "订单总数"];
    activeOption.value = {
      title: {
        text: amount ? "订单金额统计" : "订单数量统计"
      },
      tooltip: {},
      legend: {
        data: lagendData
      },
      xAxis: {
        data: xAxisData
      },
      yAxis: {},
      series: [
        {
          name: lagendData[0],
          type: "bar",
          data: seriesDataClosed
        },
        {
          name: lagendData[1],
          type: "bar",
          data: seriesDataUnpaid
        },
        {
          name: lagendData[2],
          type: "bar",
          data: seriesDataPaid
        },
        {
          name: lagendData[3],
          type: "bar",
          data: seriesDataRefunding
        },
        {
          name: lagendData[4],
          type: "bar",
          data: seriesDataRefunded
        },
        {
          name: lagendData[5],
          type: "bar",
          data: seriesDataCompleted
        },
        {
          name: lagendData[6],
          type: "bar",
          data: seriesDataTotal
        }
      ]
    };
  });
}

function handleSearch(checked = false) {
  if (!checked) {
    if (!check(true)) {
      return;
    }
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
    case "orderAmount":
      handleOrderStatistics(true);
      break;
  }
}

function somethingChange() {
  if (!check()) {
    return;
  }
  handleSearch(true);
}
</script>
