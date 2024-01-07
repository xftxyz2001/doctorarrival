<template>
  <NResult status="500" title="错误提示" :description="error.message" class="pt-10">
    <template #footer>
      <NButton @click="handleError" type="primary">返回首页</NButton>
    </template>
  </NResult>
</template>

<script setup>
import { NResult, NButton } from 'naive-ui'

const props = defineProps({
  error: Object
})

const handleError = () => clearError({ redirect: '/' })
</script>
