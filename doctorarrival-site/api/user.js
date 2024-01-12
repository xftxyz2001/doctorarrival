// 微信扫码登录参数
export function getWxLoginQrCodeParam() {
  return request({
    url: '/api/user/wx/qrcode',
    method: 'get'
  })
}