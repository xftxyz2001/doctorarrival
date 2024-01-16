export function getDictById(id) {
  return request({
    url: `/api/common/dict/id/${id}`,
    method: "get"
  });
}

export function getDictChildrenByParentId(parentId) {
  return request({
    url: `/api/common/dict/children/id/${parentId}`,
    method: "get"
  });
}

export function getDictChildrenByDictCode(dictCode) {
  return request({
    url: `/api/common/dict/children/code/${dictCode}`,
    method: "get"
  });
}
