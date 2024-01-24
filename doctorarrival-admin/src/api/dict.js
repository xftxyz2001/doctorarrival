import request from "@/utils/system/request";

export function exportDictApi() {
  return request({
    url: "/admin/common/dict/export",
    method: "post",
    responseType: "blob"
  });
}

export function importDictApi(data) {
  return request({
    url: "/admin/common/dict/import",
    method: "post",
    data
  });
}

export function getDictChildrenByParentIdApi(parentId) {
  return request({
    url: `/api/common/dict/children/id/${parentId}`,
    method: "get"
  });
}
