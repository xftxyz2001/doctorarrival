<template>
  <div class="layout-container">
    <div class="layout-container-form flex space-between">
      <div class="layout-container-form-handle">
        <el-button type="primary" :icon="Plus" @click="openAddDialog">添加</el-button>
        <el-popconfirm title="确定删除选中的数据吗？" @confirm="deleteChoose" confirm-button-text="确定" cancel-button-text="取消">
          <template #reference>
            <el-button type="danger" :icon="Delete" :disabled="chooseData.length === 0">删除选中</el-button>
          </template>
        </el-popconfirm>
      </div>
      <div class="layout-container-form-search">
        <el-input v-model="query.hospitalCode" placeholder="医院编号"></el-input>
        <el-input v-model="query.hospitalName" placeholder="医院名称"></el-input>
        <el-button type="primary" :icon="Search" class="search-btn" @click="getTableData">搜索</el-button>
      </div>
    </div>

    <div class="layout-container-table">
      <Table ref="table" v-model:page="page" v-loading="loading" row-key="id" :showSelection="true" :data="tableData"
        @getTableData="getTableData" @selection-change="handleSelectionChange">
        <el-table-column label="医院编号" prop="hospitalCode"></el-table-column>
        <el-table-column label="医院名称" prop="hospitalName"></el-table-column>
        <el-table-column label="api基础路径" prop="apiUrl"></el-table-column>
        <el-table-column label="签名秘钥" prop="signKey"></el-table-column>
        <el-table-column label="联系人" prop="contactsName"></el-table-column>
        <el-table-column label="联系人手机" prop="contactsPhone"></el-table-column>
        <el-table-column label="状态" prop="status">
          <template v-slot="scope">
            <el-button type="primary" @click="setStatus(scope.row)" v-if="scope.row.status === 1">启用</el-button>
            <el-button type="danger" @click="setStatus(scope.row)" v-if="scope.row.status === 0">禁用</el-button>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime"></el-table-column>
        <el-table-column label="更新时间" prop="updateTime"></el-table-column>

        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" @click="openEditDialog(scope.row)">修改</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="deleteOne(scope.row)" confirm-button-text="确定"
              cancel-button-text="取消">
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
        <el-form-item label="医院编号" prop="hospitalCode">
          <el-input v-model="formModel.hospitalCode" placeholder="请输入医院编号"></el-input>
        </el-form-item>
        <el-form-item label="医院名称" prop="hospitalName">
          <el-input v-model="formModel.hospitalName" placeholder="请输入医院名称"></el-input>
        </el-form-item>
        <el-form-item label="api基础路径" prop="apiUrl">
          <el-input v-model="formModel.apiUrl" placeholder="请输入api基础路径"></el-input>
        </el-form-item>
        <el-form-item label="签名秘钥" prop="signKey">
          <el-input v-model="formModel.signKey" placeholder="请输入签名秘钥"></el-input>
        </el-form-item>
        <el-form-item label="联系人" prop="contactsName">
          <el-input v-model="formModel.contactsName" placeholder="请输入联系人"></el-input>
        </el-form-item>
        <el-form-item label="联系人手机" prop="contactsPhone">
          <el-input v-model="formModel.contactsPhone" placeholder="请输入联系人手机"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formModel.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </Layer>
  </div>
</template>

<script setup>
import { saveApi, updateApi, removeApi, removeBatchApi, getByIdApi, findApi, setStatusApi } from '@/api/hospital'
import { ref, reactive, onBeforeMount } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus, Delete, Search } from '@element-plus/icons'
import Table from '@/components/table/index.vue'
import Layer from '@/components/layer/index.vue'

// 搜索相关
const query = reactive({
  hospitalCode: '',
  hospitalName: ''
})

// 弹窗控制器
const layer = reactive({
  show: false,
  title: '新增',
  showButton: true
})

const formModel = ref({
  hospitalCode: '',
  hospitalName: '',
  apiUrl: '',
  signKey: '',
  contactsName: '',
  contactsPhone: '',
  status: 0
})

// 分页参数, 供table使用
const page = reactive({
  index: 1,
  size: 20,
  total: 0
})

const loading = ref(true)
const tableData = ref([])
const chooseData = ref([])
const handleSelectionChange = val => {
  chooseData.value = val
}

// 获取表格数据
function getTableData() {
  findApi(query, page.index, page.size).then(res => {
    tableData.value = res.records
    // page.index = res.current
    // page.size = res.size
    page.total = res.total
    loading.value = false
  })
}

onBeforeMount(() => {
  getTableData()
})

// 切换状态
function setStatus(row) {
  setStatusApi(row.id, row.status === 1 ? 0 : 1).then(res => {
    ElMessage.success('操作成功')
    getTableData()
  })
}

// 删除选中数据
function deleteChoose() {
  removeBatchApi(chooseData.value.map(item => item.id)).then(res => {
    ElMessage.success('删除成功')
    getTableData()
  })
}

// 删除单条数据
function deleteOne(row) {
  removeApi(row.id).then(res => {
    ElMessage.success('删除成功')
    getTableData()
  })
}

// 新增/编辑弹窗
function openAddDialog() {
  formModel.value = {
    hospitalCode: '',
    hospitalName: '',
    apiUrl: '',
    signKey: '',
    contactsName: '',
    contactsPhone: '',
    status: 0
  }

  layer.title = '新增记录'
  layer.show = true
  layer.showButton = true
}

function openEditDialog(row) {
  getByIdApi(row.id).then(res => {
    formModel.value = JSON.parse(JSON.stringify(res))
  })
  layer.title = '编辑记录'
  layer.show = true
  layer.showButton = true
}

// 提交新增/修改
function submit() {
  if (formModel.value.id) {
    updateApi(formModel.value).then(res => {
      ElMessage.success('修改成功')
      layer.show = false
      getTableData()
    })
  } else {
    saveApi(formModel.value).then(res => {
      ElMessage.success('新增成功')
      layer.show = false
      getTableData()
    })
  }
}
</script>
