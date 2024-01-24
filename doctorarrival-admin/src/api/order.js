import request from "@/utils/system/request";

export function saveApi(data) {
  return request({
    url: "/admin/order/info/save",
    method: "post",
    data
  });
}

export function removeApi(id) {
  return request({
    url: `/admin/order/info/remove/id/${id}`,
    method: "delete"
  });
}

export function removeBatchApi(idList) {
  return request({
    url: "/admin/order/info/remove/batch",
    method: "delete",
    data: idList
  });
}

export function updateApi(data) {
  return request({
    url: "/admin/order/info/update",
    method: "put",
    data
  });
}

export function setStatusApi(id, status) {
  return request({
    url: `/admin/order/info/status/${id}/${status}`,
    method: "put"
  });
}

export function getByIdApi(id) {
  return request({
    url: `/admin/order/info/id/${id}`,
    method: "get"
  });
}

export function findApi(data, current = 1, size = 20) {
  return request({
    url: "/admin/order/info/find",
    method: "post",
    params: { current, size },
    data
  });
}
