<template>
  <div class="layout-container">
    <div class="layout-container-form flex space-between">
      <div class="layout-container-form-handle">
        <el-button type="primary" :icon="Upload" @click="openUploadDialog">上传</el-button>
        <el-button
          type="primary"
          :icon="Download"
          :disabled="selectData.length === 0"
          @click="downloadSelect(selectData)"
        >
          下载选中
        </el-button>
        <el-popconfirm
          title="确定删除选中的数据吗？"
          @confirm="deleteSelect(selectData)"
          confirm-button-text="确定"
          cancel-button-text="取消"
        >
          <template #reference>
            <el-button type="danger" :icon="Delete" :disabled="selectData.length === 0">删除选中</el-button>
          </template>
        </el-popconfirm>
      </div>
      <el-button type="primary" @click="go" style="float: right">前往阿里云OSS管理控制台</el-button>
    </div>

    <div class="layout-container-table">
      <div class="system-table-box">
        <el-table
          :data="tableData"
          v-infinite-scroll="load"
          :infinite-scroll-disabled="busy"
          infinite-scroll-distance="10"
          class="system-table"
          style="overflow: visible; width: 100%"
          row-key="name"
          border
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>

          <el-table-column label="文件名">
            <template v-slot="scope">
              <span>{{ scope.row.name }}</span>
            </template>
          </el-table-column>
          <el-table-column label="文件大小(B)">
            <template v-slot="{ row }">
              {{ row.size }}
            </template>
          </el-table-column>
          <el-table-column label="最后修改时间">
            <template v-slot="scope">
              <span>{{ scope.row.lastModified }}</span>
            </template>
          </el-table-column>
          <el-table-column label="文件类型">
            <template v-slot="scope">
              <span>{{ scope.row.type }}</span>
            </template>
          </el-table-column>

          <el-table-column label="操作">
            <template v-slot="scope">
              <el-button type="primary" @click="downloadRow(scope.row.name)">下载</el-button>
              <el-popconfirm
                title="确定删除吗？"
                @confirm="deleteRow(scope.row.name)"
                confirm-button-text="确定"
                cancel-button-text="取消"
              >
                <template #reference>
                  <el-button type="danger">删除</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <Layer :layer="layer" @confirm="submit">
      <el-upload :file-list="fileList" :on-change="handleChange" :auto-upload="false" multiple>
        <el-button type="primary">点击选择文件</el-button>
      </el-upload>
    </Layer>
  </div>
</template>

<script setup>
import {
  deleteApi,
  deleteBatchApi,
  downloadApi,
  downloadBatchApi,
  getAdminPathApi,
  listApi,
  uploadBatchApi
} from "@/api/oss";
import Layer from "@/components/layer/index.vue";
import { Delete, Download, Upload } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import { onBeforeMount, reactive, ref } from "vue";

// 表格数据
const tableData = ref([]);
const busy = ref(false);

let listRequestOptions = {
  continuationToken: null,
  maxKeys: 10
};

// 获取数据
function load() {
  busy.value = true;
  if (listRequestOptions.continuationToken === null) {
    tableData.value = [];
  }
  listApi(listRequestOptions).then(res => {
    tableData.value = tableData.value.concat(res.objectSummaries);
    listRequestOptions.continuationToken = res.nextContinuationToken;
    if (listRequestOptions.continuationToken === null) {
      busy.value = true;
    } else {
      busy.value = false;
    }
  });
}

onBeforeMount(() => {
  load();
});

// 选择的数据
const selectData = ref([]);

function handleSelectionChange(val) {
  selectData.value = val;
}

// 下载
function downloadRow(name) {
  downloadApi(name).then(res => {
    const blob = new Blob([res.data], { type: res.headers["content-type"] });
    const downloadElement = document.createElement("a");
    const href = window.URL.createObjectURL(blob);
    downloadElement.href = href;
    downloadElement.download = res.headers["content-disposition"].match(/filename="(.*)"/)[1];
    document.body.appendChild(downloadElement);
    downloadElement.click();
    document.body.removeChild(downloadElement);
    window.URL.revokeObjectURL(href);
  });
}
// 删除
function deleteRow(name) {
  deleteApi(name).then(res => {
    ElMessage({
      message: "删除成功",
      type: "success"
    });

    // 删除成功后清除表中对应的数据
    const index = tableData.value.findIndex(row => row.name === name);
    tableData.value.splice(index, 1);
  });
}

// 下载选中
function downloadSelect(selectData) {
  const names = selectData.map(item => item.name);
  downloadBatchApi(names).then(res => {
    const blob = new Blob([res.data], { type: res.headers["content-type"] });
    const downloadElement = document.createElement("a");
    const href = window.URL.createObjectURL(blob);
    downloadElement.href = href;
    downloadElement.download = res.headers["content-disposition"].match(/filename="(.*)"/)[1];
    document.body.appendChild(downloadElement);
    downloadElement.click();
    document.body.removeChild(downloadElement);
    window.URL.revokeObjectURL(href);
  });
}
// 删除选中
function deleteSelect(selectData) {
  const names = selectData.map(item => item.name);
  deleteBatchApi(names).then(res => {
    ElMessage({
      message: "删除成功",
      type: "success"
    });

    // 删除成功后清除表中对应的数据
    selectData.forEach(item => {
      const index = tableData.value.findIndex(row => row.name === item.name);
      tableData.value.splice(index, 1);
    });
    // 清空选中的数据
    selectData.value = [];
  });
}

// 上传文件弹出层
const layer = reactive({
  show: false,
  title: "上传文件",
  showButton: true
});

function openUploadDialog() {
  layer.show = true;
}

const fileList = ref([]);
function handleChange(file, fileLst) {
  fileList.value = fileLst;
}
function submit() {
  const formData = new FormData();
  fileList.value.forEach(file => {
    formData.append("files", file.raw);
  });
  uploadBatchApi(formData).then(res => {
    layer.show = false;
    ElMessage({
      message: "上传成功",
      type: "success"
    });
    fileList.value = [];
    load();
  });
}

// 前往管理
function go() {
  getAdminPathApi().then(res => {
    window.open(res);
  });
}
</script>
