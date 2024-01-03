import request from '@/utils/system/request'

export function getAdminPathApi() {
  return request({
    url: '/admin/oss/file/path',
    method: 'get'
  })
}

export function uploadApi(data) {
  return request({
    url: '/admin/oss/file/upload',
    method: 'post',
    data
  })
}

export function uploadBatchApi(data) {
  return request({
    url: '/admin/oss/file/upload/batch',
    method: 'post',
    data
  })
}

export function downloadApi(data) {
  return request({
    url: `/admin/oss/file/download/${data}`,
    method: 'get',
    responseType: 'blob'
  })
}

export function downloadBatchApi(data) {
  return request({
    url: '/admin/oss/file/download/batch',
    method: 'post',
    responseType: 'blob',
    data
  })
}

export function deleteApi(data) {
  return request({
    url: `/admin/oss/file/delete/${data}`,
    method: 'delete'
  })
}

export function deleteBatchApi(data) {
  return request({
    url: '/admin/oss/file/delete/batch',
    method: 'delete',
    data
  })
}

export function listApi(params) {
  return request({
    url: '/admin/oss/file/list',
    method: 'get',
    params
  })
}
