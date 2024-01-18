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

export function getOrderList(current = 1, size = 10) {
  return request({
    url: "/api/order/info/auth/list",
    method: "get",
    params: {
      current,
      size
    }
  });
}

export function cancelOrder(orderId) {
  return request({
    url: `/api/order/info/auth/cancel/${orderId}`,
    method: "put"
  });
}
