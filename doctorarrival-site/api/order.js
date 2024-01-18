export function submitOrder(data) {
  return request({
    url: "/api/order/info/auth/submit",
    method: "post",
    data
  });
}

export function getOrderDetail(orderId) {
  return request({
    url: `/api/order/info/auth/detail/${orderId}`,
    method: "get"
  });
}

export function getOrderList(data, current = 1, size = 10) {
  return request({
    url: "/api/order/info/auth/list",
    method: "post",
    params: {
      current,
      size
    },
    data
  });
}

export function cancelOrder(orderId) {
  return request({
    url: `/api/order/info/auth/cancel/${orderId}`,
    method: "put"
  });
}

// alipay
export function getPayPage(orderId) {
  return request({
    url: `/api/order/alipay/auth/pay/${orderId}`,
    method: "get"
  });
}

export function queryOrder(orderId) {
  return request({
    url: `/api/order/alipay/auth/query/${orderId}`,
    method: "get"
  });
}
