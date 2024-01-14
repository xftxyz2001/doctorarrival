// 医院
export function findHospitalByHospitalName(hospitalName) {
  return request({
    url: '/api/hospital/find/name',
    method: 'get',
    params: { hospitalName }
  })
}

export function findHospitalByHospitalCode(hospitalCode) {
  return request({
    url: `/api/hospital/find/code/${hospitalCode}`,
    method: 'get'
  })
}

export function findHospitalPage(searchObj, current, size) {
  return request({
    url: '/api/hospital/find/page',
    method: 'post',
    prams: { current, size },
    data: searchObj
  })
}

// 科室
export function getDepartmentByHospitalCode(hospitalCode) {
  return request({
    url: `/api/hospital/department/hospital/code/${hospitalCode}`,
    method: 'get'
  })
}
