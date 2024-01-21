// 医院
export function findHospitalByHospitalName(hospitalName) {
  return request({
    url: "/api/hospital/find/name",
    method: "get",
    params: { hospitalName }
  });
}

export function findHospitalByHospitalCode(hospitalCode) {
  return request({
    url: `/api/hospital/find/code/${hospitalCode}`,
    method: "get"
  });
}

export function findHospitalPage(searchObj, current, size) {
  return request({
    url: "/api/hospital/find/page",
    method: "post",
    params: { current, size },
    data: searchObj
  });
}

// 科室
export function getDepartmentByHospitalCode(hospitalCode) {
  return request({
    url: `/api/hospital/department/hospital/code/${hospitalCode}`,
    method: "get"
  });
}

export function getDepartmentByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode) {
  return request({
    url: `/api/hospital/department/hospital/department/code`,
    method: "get",
    params: { hospitalCode, departmentCode }
  });
}

// 排班
export function getSchedulePage(hospitalCode, departmentCode, current = 1, size = 7) {
  return request({
    url: `/api/hospital/schedule/page`,
    method: "get",
    params: { hospitalCode, departmentCode, current, size }
  });
}

export function getScheduleByHospitalCodeAndDepartmentCodeAndWorkDate(hospitalCode, departmentCode, workDate) {
  return request({
    url: `/api/hospital/schedule/hospital/department/date`,
    method: "get",
    params: { hospitalCode, departmentCode, workDate }
  });
}

export function getScheduleById(id) {
  return request({
    url: `/api/hospital/schedule/id/${id}`,
    method: "get"
  });
}

// join
export function join(data) {
  return request({
    url: "/api/hospital/side/join",
    method: "post",
    data,
    responseType: "blob"
  });
}
