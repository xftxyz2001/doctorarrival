export function login(data) {
  return request({
    url: '/api/user/info/login',
    method: 'post',
    data
  })
}

export function getUserInfoBasic() {
  return request({
    url: '/api/user/info/auth/basic',
    method: 'get'
  })
}

export function getUserInfoDetail() {
  return request({
    url: '/api/user/info/auth/detail',
    method: 'get'
  })
}

// 微信扫码登录参数
export function getWxLoginQrCodeParam() {
  return request({
    url: '/api/user/wx/qrcode',
    method: 'get'
  })
}

// 保存实名信息
export function saveRealName(data) {
  return request({
    url: '/api/user/info/auth/realname',
    method: 'post',
    data
  })
}

// 获取就诊人列表
export function getPatientList() {
  return request({
    url: '/api/user/patient/auth/list',
    method: 'get'
  })
}

export function getPatientDetail(patientId) {
  return request({
    url: `/api/user/patient/auth/detail/${patientId}`,
    method: 'get'
  })
}

export function removePatient(patientId) {
  return request({
    url: `/api/user/patient/auth/remove/${patientId}`,
    method: 'delete'
  })
}