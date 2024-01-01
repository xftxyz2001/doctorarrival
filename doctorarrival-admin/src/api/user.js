import request from '@/utils/system/request'

/** 检查是否有权限 */
export function checkAuthorizationApi() {
  return request({
    url: '/admin/common/check',
    method: 'post'
  })
}


// ----------------- 以下为mock接口 -----------------
/** 登录api */
export function loginApi(data) {
  return request({
    url: '/user/login',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

/** 获取用户信息Api */
export function getInfoApi(data) {
  return request({
    url: '/user/info',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

/** 退出登录Api */
export function loginOutApi() {
  return request({
    url: '/user/out',
    method: 'post',
    baseURL: '/mock'
  })
}

/** 获取用户信息Api */
export function passwordChange(data) {
  return request({
    url: '/user/passwordChange',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

/** 获取登录后需要展示的菜单 */
export function getMenuApi() {
  return request({
    url: '/menu/list',
    method: 'post',
    baseURL: '/mock'
  })
}