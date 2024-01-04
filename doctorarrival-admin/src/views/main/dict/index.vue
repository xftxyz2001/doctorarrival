<template>
  <div class="layout-container">
    <div class="layout-container-form flex space-between">
      <div class="layout-container-form-handle">
        <el-button type="primary" :icon="Download" @click="handleExport">导出</el-button>
        <el-button type="primary" :icon="Upload" @click="openImportDialog">导入</el-button>
      </div>
    </div>

    <div class="layout-container-table">
      <el-table :data="tableData" style="width: 100%" row-key="id" border lazy :load="getChildrens"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">

        <el-table-column label="名称">
          <template v-slot="scope">
            <span>{{ scope.row.value }}</span>
          </template>
        </el-table-column>
        <el-table-column label="编码">
          <template v-slot="{ row }">
            {{ row.dictCode }}
          </template>
        </el-table-column>
        <el-table-column label="值">
          <template v-slot="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <Layer :layer="layer" @confirm="submit">
      <el-upload :on-change="handleChange" :auto-upload="false" :accept="'.xls,.xlsx'">
        <el-button type="primary">点击选择文件（仅支持xls、xlsx格式）</el-button>
      </el-upload>
    </Layer>

  </div>
</template>

<script setup>
import { exportDictApi, getDictChildrenByParentIdApi, importDictApi } from '@/api/dict'
import Layer from '@/components/layer/index.vue'
import { Download, Upload } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { onBeforeMount, reactive, ref } from 'vue'

function handleExport() {
  exportDictApi().then(res => {
    const blob = new Blob([res.data], { type: res.headers['content-type'] })
    const downloadElement = document.createElement('a')
    const href = window.URL.createObjectURL(blob)
    downloadElement.href = href
    downloadElement.download = res.headers['content-disposition'].match(/filename="(.*)"/)[1]
    document.body.appendChild(downloadElement)
    downloadElement.click()
    document.body.removeChild(downloadElement)
    window.URL.revokeObjectURL(href)
  })
}

// 表格数据
const tableData = ref([])

function getDictList(parentId) {
  getDictChildrenByParentIdApi(parentId).then(res => {
    tableData.value = res
  })
}

onBeforeMount(() => {
  getDictList(1)
})

function getChildrens(row, treeNode, resolve) {
  getDictChildrenByParentIdApi(row.id).then(res => {
    row.children = res
    resolve(row.children)
  })
}

// 导入数据字典弹出层
const layer = reactive({
  show: false,
  title: '导入数据字典',
  showButton: true,
})

function openImportDialog() {
  layer.show = true
}

let selectFile = null

function handleChange(file, fileList) {
  selectFile = file
  fileList.splice(0, fileList.length - 1)
}

function submit() {
  const formData = new FormData()
  formData.append('file', selectFile.raw)
  importDictApi(formData).then(res => {
    layer.show = false
    ElMessage({
      message: '导入成功',
      type: 'success'
    })
    getDictList(1)
  })
}
</script>
