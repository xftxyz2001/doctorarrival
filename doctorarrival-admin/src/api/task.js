import request from "@/utils/system/request";

export function visitNotificationApi() {
  return request({
    url: "/admin/task/manual/visit/notification",
    method: "post"
  });
}

export function updateOrderStatusApi() {
  return request({
    url: "/admin/task/manual/update/order/status",
    method: "post"
  });
}
