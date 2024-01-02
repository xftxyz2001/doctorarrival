import request from '@/utils/system/request'

export function getAdminPathApi() {
  return request({
    url: '/admin/oss/file/path',
    method: 'get'
  })
}