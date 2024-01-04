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
        <el-input v-model="query.phone" placeholder="手机号"></el-input>
        <el-input v-model="query.nickName" placeholder="昵称"></el-input>
        <el-button type="primary" :icon="Search" class="search-btn" @click="getTableData">搜索</el-button>
      </div>
    </div>

    <div class="layout-container-table">
      <Table ref="table" v-model:page="page" v-loading="loading" row-key="id" :showSelection="true" :data="tableData"
        @getTableData="getTableData" @selection-change="handleSelectionChange">
        <el-table-column label="手机号" prop="phone"></el-table-column>
        <el-table-column label="微信id" prop="openid"></el-table-column>
        <el-table-column label="昵称" prop="nickName"></el-table-column>
        <el-table-column label="实名认证信息" prop="authStatus">
          <template v-slot="scope">
            <!-- <el-button type="primary" @click="openEditDialog(scope.row)" v-if="scope.row.authStatus === 0">未认证</el-button>
            <el-button type="primary" @click="openEditDialog(scope.row)" v-if="scope.row.authStatus === 1">认证中</el-button>
            <el-button type="primary" @click="openEditDialog(scope.row)" v-if="scope.row.authStatus === 2">认证成功</el-button>
            <el-button type="danger" @click="openEditDialog(scope.row)" v-if="scope.row.authStatus === -1">认证失败</el-button> -->
            <!-- 认证成功显示查看，否则显示暂无 -->
            <el-button type="primary" @click="lookAuthData(scope.row)" v-if="scope.row.authStatus === 2">查看</el-button>
            <el-button type="info" v-else>暂无</el-button>
          </template>
        </el-table-column>
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
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="formModel.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="微信id" prop="openid">
          <el-input v-model="formModel.openid" placeholder="请输入微信id"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="formModel.nickName" placeholder="请输入昵称"></el-input>
        </el-form-item>

        <el-form-item label="姓名" prop="name">
          <el-input v-model="formModel.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="证件类型" prop="certificatesType">
          <el-select v-model="formModel.certificatesType" placeholder="请选择证件类型">
            <el-option label="身份证" :value="1"></el-option>
            <el-option label="军官证" :value="2"></el-option>
            <el-option label="护照" :value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证件编号" prop="certificatesNo">
          <el-input v-model="formModel.certificatesNo" placeholder="请输入证件编号"></el-input>
        </el-form-item>
        <el-form-item label="证件照片路径" prop="certificatesUrl">
          <el-input v-model="formModel.certificatesUrl" placeholder="请输入证件照片路径"></el-input>
        </el-form-item>
        <el-form-item label="实名认证状态" prop="authStatus">
          <el-select v-model="formModel.authStatus" placeholder="请选择实名认证状态">
            <el-option label="未认证" :value="0"></el-option>
            <el-option label="认证中" :value="1"></el-option>
            <el-option label="认证成功" :value="2"></el-option>
            <el-option label="认证失败" :value="-1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="认证时间" prop="authTime">
          <el-input v-model="formModel.authTime" placeholder="请输入认证时间"></el-input>
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formModel.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </Layer>

    <!-- 查看证件信息弹出层 -->
    <Layer :layer="lookAuthLayer">
      <el-form ref="form" :model="formModel" label-width="100px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formModel.name" placeholder="请输入姓名" disabled></el-input>
        </el-form-item>
        <el-form-item label="证件类型" prop="certificatesType">
          <el-select v-model="formModel.certificatesType" placeholder="请选择证件类型" disabled>
            <el-option label="身份证" :value="1"></el-option>
            <el-option label="军官证" :value="2"></el-option>
            <el-option label="护照" :value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证件编号" prop="certificatesNo">
          <el-input v-model="formModel.certificatesNo" placeholder="请输入证件编号" disabled></el-input>
        </el-form-item>
        <el-form-item label="证件照片路径" prop="certificatesUrl">
          <el-input v-model="formModel.certificatesUrl" placeholder="请输入证件照片路径" disabled></el-input>
          <!-- <el-tooltip content="点击下载图片" placement="top">
            <el-input v-model="formModel.certificatesUrl" placeholder="请输入证件照片路径" disabled
              @click.native="downloadImg(formModel.certificatesUrl)">
            </el-input>
          </el-tooltip> -->
        </el-form-item>
        <el-form-item label="实名认证状态" prop="authStatus">
          <el-select v-model="formModel.authStatus" placeholder="请选择实名认证状态" disabled>
            <el-option label="未认证" :value="0"></el-option>
            <el-option label="认证中" :value="1"></el-option>
            <el-option label="认证成功" :value="2"></el-option>
            <el-option label="认证失败" :value="-1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="认证时间" prop="authTime">
          <el-input v-model="formModel.authTime" placeholder="请输入认证时间" disabled></el-input>
        </el-form-item>
      </el-form>
    </Layer>
  </div>
</template>
  
<script setup>
import { findApi, getByIdApi, removeApi, removeBatchApi, saveApi, setStatusApi, updateApi } from '@/api/user'
import Layer from '@/components/layer/index.vue'
import Table from '@/components/table/index.vue'
import { Delete, Plus, Search } from '@element-plus/icons'
import { ElMessage } from 'element-plus'
import { onBeforeMount, reactive, ref } from 'vue'

// 搜索相关
const query = reactive({
  phone: '',
  nickName: '',
})

// 弹窗控制器
const layer = reactive({
  show: false,
  title: '新增',
  showButton: true
})

const formModel = ref({
  phone: '',
  openid: '',
  nickName: '',
  certificatesType: '',
  certificatesNo: '',
  certificatesUrl: '',
  authStatus: '',
  authTime: '',
  status: 0,
})

const lookAuthLayer = reactive({
  show: false,
  title: '查看证件信息',
  showButton: false
})

const lookAuthData = (row) => {
  getByIdApi(row.id).then(res => {
    formModel.value = JSON.parse(JSON.stringify(res))
  })
  lookAuthLayer.show = true
}

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
    phone: '',
    openid: '',
    nickName: '',
    certificatesType: '',
    certificatesNo: '',
    certificatesUrl: '',
    authStatus: '',
    authTime: '',
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
