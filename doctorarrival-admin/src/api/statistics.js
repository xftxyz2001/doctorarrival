import request from "@/utils/system/request";

export function hospitalStatistics(data = {}) {
  return request({
    url: "/admin/statistics/analysis/hospital",
    method: "post",
    data
  });
}

export function userStatistics(data = {}) {
  return request({
    url: "/admin/statistics/analysis/user",
    method: "post",
    data
  });
}

export function orderStatistics(data = {}) {
  return request({
    url: "/admin/statistics/analysis/order",
    method: "post",
    data
  });
}
