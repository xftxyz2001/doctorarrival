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