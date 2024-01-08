<template>
  <el-result icon="error" title="错误提示" :sub-title="error.message">
    <template #extra>
      <el-button type="primary" @click="handleError">Back</el-button>
    </template>
  </el-result>
</template>

<script setup>
const props = defineProps({
  error: Object
})

// for debug
console.error(props.error)

const handleError = () => clearError({ redirect: '/' })
</script>
