import request from "@/utils/system/request";

export function saveApi(data) {
  return request({
    url: "/admin/hospital/set/save",
    method: "post",
    data
  });
}

export function updateApi(data) {
  return request({
    url: "/admin/hospital/set/update",
    method: "put",
    data
  });
}

export function removeApi(id) {
  return request({
    url: `/admin/hospital/set/remove/id/${id}`,
    method: "delete"
  });
}

export function removeBatchApi(idList) {
  return request({
    url: "/admin/hospital/set/remove/batch",
    method: "delete",
    data: idList
  });
}

export function getByIdApi(id) {
  return request({
    url: `/admin/hospital/set/id/${id}`,
    method: "get"
  });
}

export function findApi(data, current = 1, size = 20) {
  return request({
    url: "/admin/hospital/set/find",
    method: "post",
    params: { current, size },
    data
  });
}

export function setStatusApi(id, status) {
  return request({
    url: `/admin/hospital/set/status/${id}/${status}`,
    method: "put"
  });
}
