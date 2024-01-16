export function uploadCertificates(data) {
  return request({
    url: "/api/oss/file/upload",
    method: "post",
    data
  });
}

export function previewCertificate(url) {
  return request({
    url: `/api/oss/file/preview/${url}`,
    method: "get"
  });
}
