export function submitOrder(data) {
  return request({
    url: "/api/order/info/auth/submit",
    method: "post",
    data
  });
}
