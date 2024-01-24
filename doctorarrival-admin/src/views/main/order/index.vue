<template>
  <div class="layout-container">
    <div class="layout-container-form flex space-between">
      <div class="layout-container-form-handle">
        <el-button type="primary" :icon="Plus" @click="openAddDialog">添加</el-button>
        <el-popconfirm
          title="确定删除选中的数据吗？"
          @confirm="deleteChoose"
          confirm-button-text="确定"
          cancel-button-text="取消"
        >
          <template #reference>
            <el-button type="danger" :icon="Delete" :disabled="chooseData.length === 0">删除选中</el-button>
          </template>
        </el-popconfirm>
      </div>
      <div class="layout-container-form-search">
        <el-input v-model="query.hospitalCodeOrName" placeholder="医院编号或名称"></el-input>
        <el-input v-model="query.departmentCodeOrName" placeholder="科室编号或名称"></el-input>
        <el-input v-model="query.doctorName" placeholder="医生名称"></el-input>
        <el-input v-model="query.patientIdOrName" placeholder="就诊人编号或名称"></el-input>
        <el-select v-model="query.orderStatus" placeholder="订单状态" style="width: 100%">
          <el-option label="已关闭" :value="-1"></el-option>
          <el-option label="待支付" :value="0"></el-option>
          <el-option label="已支付" :value="1"></el-option>
          <el-option label="待退款" :value="2"></el-option>
          <el-option label="已退款" :value="3"></el-option>
          <el-option label="已完成" :value="4"></el-option>
          <el-option label="全部" :value="null"></el-option>
        </el-select>
        <el-button type="primary" :icon="Search" class="search-btn" @click="getTableData">搜索</el-button>
      </div>
    </div>

    <div class="layout-container-table">
      <Table
        ref="table"
        v-model:page="page"
        v-loading="loading"
        row-key="id"
        :showSelection="true"
        :data="tableData"
        @getTableData="getTableData"
        @selection-change="handleSelectionChange"
      >
        <el-table-column label="订单号" prop="id" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column label="用户id" prop="userId" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column label="描述" :show-overflow-tooltip="true">
          <template v-slot="scope">
            {{ scope.row.hospitalName }} - {{ scope.row.departmentName }} - {{ scope.row.doctorName }} -
            {{ scope.row.reserveDate }}
          </template>
        </el-table-column>
        <el-table-column label="就诊人" prop="patientName"></el-table-column>
        <el-table-column label="联系方式" prop="patientPhone"></el-table-column>
        <el-table-column label="金额" prop="amount"></el-table-column>
        <el-table-column label="订单状态" prop="orderStatus">
          <!-- 订单状态（-1：已关闭，0：待支付，1：已支付，2：待退款，3：已退款，4：已完成） -->
          <template v-slot="scope">
            <el-tag v-if="scope.row.orderStatus === -1" type="danger">已关闭</el-tag>
            <el-tag v-if="scope.row.orderStatus === 0" type="info">待支付</el-tag>
            <el-tag v-if="scope.row.orderStatus === 1" type="success">已支付</el-tag>
            <el-tag v-if="scope.row.orderStatus === 2" type="warning">待退款</el-tag>
            <el-tag v-if="scope.row.orderStatus === 3" type="danger">已退款</el-tag>
            <el-tag v-if="scope.row.orderStatus === 4">已完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column label="更新时间" prop="updateTime" :show-overflow-tooltip="true"></el-table-column>

        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" @click="openEditDialog(scope.row)">修改</el-button>
            <el-popconfirm
              title="确定删除吗？"
              @confirm="deleteOne(scope.row)"
              confirm-button-text="确定"
              cancel-button-text="取消"
            >
              <template #reference>
                <el-button type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </Table>
    </div>

    <!-- 新增/修改 弹出层 -->
    <Layer :layer="layer" @confirm="submit">
      <el-form ref="form" :model="formModel" label-width="100px">
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="formModel.userId" placeholder="请输入用户id"></el-input>
        </el-form-item>
        <el-form-item label="医院编号" prop="hospitalCode">
          <el-input v-model="formModel.hospitalCode" placeholder="请输入医院编号"></el-input>
        </el-form-item>
        <el-form-item label="医院名称" prop="hospitalName">
          <el-input v-model="formModel.hospitalName" placeholder="请输入医院名称"></el-input>
        </el-form-item>
        <el-form-item label="科室编号" prop="departmentCode">
          <el-input v-model="formModel.departmentCode" placeholder="请输入科室编号"></el-input>
        </el-form-item>
        <el-form-item label="科室名称" prop="departmentName">
          <el-input v-model="formModel.departmentName" placeholder="请输入科室名称"></el-input>
        </el-form-item>
        <el-form-item label="医生名称" prop="doctorName">
          <el-input v-model="formModel.doctorName" placeholder="请输入医生名称"></el-input>
        </el-form-item>
        <el-form-item label="医生职称" prop="doctorTitle">
          <el-input v-model="formModel.doctorTitle" placeholder="请输入医生职称"></el-input>
        </el-form-item>
        <el-form-item label="排班id" prop="scheduleId">
          <el-input v-model="formModel.scheduleId" placeholder="请输入排班id"></el-input>
        </el-form-item>
        <el-form-item label="预约日期" prop="reserveDate">
          <el-input v-model="formModel.reserveDate" placeholder="请输入预约日期"></el-input>
        </el-form-item>
        <el-form-item label="就诊人id" prop="patientId">
          <el-input v-model="formModel.patientId" placeholder="请输入就诊人id"></el-input>
        </el-form-item>
        <el-form-item label="就诊人名称" prop="patientName">
          <el-input v-model="formModel.patientName" placeholder="请输入就诊人名称"></el-input>
        </el-form-item>
        <el-form-item label="就诊人联系方式" prop="patientPhone">
          <el-input v-model="formModel.patientPhone" placeholder="请输入就诊人联系方式"></el-input>
        </el-form-item>
        <el-form-item label="金额（分）" prop="amount">
          <el-input v-model="formModel.amount" placeholder="请输入金额（分）"></el-input>
        </el-form-item>
        <el-form-item label="订单状态" prop="orderStatus">
          <el-select v-model="formModel.orderStatus" placeholder="请选择订单状态">
            <el-option label="已关闭" :value="-1"></el-option>
            <el-option label="待支付" :value="0"></el-option>
            <el-option label="已支付" :value="1"></el-option>
            <el-option label="待退款" :value="2"></el-option>
            <el-option label="已退款" :value="3"></el-option>
            <el-option label="已完成" :value="4"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </Layer>
  </div>
</template>

<script setup>
import { findApi, getByIdApi, removeApi, removeBatchApi, saveApi, updateApi } from "@/api/order";
import Layer from "@/components/layer/index.vue";
import Table from "@/components/table/index.vue";
import { Delete, Plus, Search } from "@element-plus/icons";
import { ElMessage } from "element-plus";
import { onBeforeMount, reactive, ref } from "vue";

// 搜索相关
const query = reactive({
  hospitalCodeOrName: "",
  departmentCodeOrName: "",
  doctorName: "",
  patientIdOrName: "",
  orderStatus: null
});

// 弹窗控制器
const layer = reactive({
  show: false,
  title: "新增",
  showButton: true
});

const formModel = ref({
  userId: "",
  hospitalCode: "",
  hospitalName: "",
  departmentCode: "",
  departmentName: "",
  doctorName: "",
  doctorTitle: "",
  scheduleId: "",
  reserveDate: "",
  patientId: "",
  patientName: "",
  patientPhone: "",
  amount: "",
  orderStatus: 0
});

// 分页参数, 供table使用
const page = reactive({
  index: 1,
  size: 20,
  total: 0
});

const loading = ref(true);
const tableData = ref([]);
const chooseData = ref([]);
const handleSelectionChange = val => {
  chooseData.value = val;
};

// 获取表格数据
function getTableData() {
  findApi(query, page.index, page.size).then(res => {
    tableData.value = res.records;
    // page.index = res.current
    // page.size = res.size
    page.total = res.total;
    loading.value = false;
  });
}

onBeforeMount(() => {
  getTableData();
});

// 删除选中数据
function deleteChoose() {
  removeBatchApi(chooseData.value.map(item => item.id)).then(res => {
    ElMessage.success("删除成功");
    getTableData();
  });
}

// 删除单条数据
function deleteOne(row) {
  removeApi(row.id).then(res => {
    ElMessage.success("删除成功");
    getTableData();
  });
}

// 新增/编辑弹窗
function openAddDialog() {
  formModel.value = {
    userId: "",
    hospitalCode: "",
    hospitalName: "",
    departmentCode: "",
    departmentName: "",
    doctorName: "",
    doctorTitle: "",
    scheduleId: "",
    reserveDate: "",
    patientId: "",
    patientName: "",
    patientPhone: "",
    amount: "",
    orderStatus: 0
  };

  layer.title = "新增记录";
  layer.show = true;
  layer.showButton = true;
}

function openEditDialog(row) {
  getByIdApi(row.id).then(res => {
    formModel.value = JSON.parse(JSON.stringify(res));
  });
  layer.title = "编辑记录";
  layer.show = true;
  layer.showButton = true;
}

// 提交新增/修改
function submit() {
  if (formModel.value.id) {
    updateApi(formModel.value).then(res => {
      ElMessage.success("修改成功");
      layer.show = false;
      getTableData();
    });
  } else {
    saveApi(formModel.value).then(res => {
      ElMessage.success("新增成功");
      layer.show = false;
      getTableData();
    });
  }
}
</script>
