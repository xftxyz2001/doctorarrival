export function getDictById(id) {
    return request(`/api/common/dict/id/${id}`, {
        method: 'get'
    })
}

export function getDictChildrenByParentId(parentId) {
    return request(`/api/common/dict/children/id/${parentId}`, {
        method: 'get'
    })
}

export function getDictChildrenByDictCode(dictCode) {
    return request(`/api/common/dict/children/code/${dictCode}`, {
        method: 'get'
    })
}
