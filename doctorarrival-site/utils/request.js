import axios from "axios";

const service = axios.create({
  baseURL: `http://${window.location.hostname}`,
  timeout: 5000
});

service.interceptors.request.use(
  config => {
    const token = useToken().value?.token;
    if (token) {
      config.headers["Authorization"] = token;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

service.interceptors.response.use(
  response => {
    // 非json数据直接返回
    if (response.headers["content-type"].indexOf("application/json") === -1) {
      return response;
    }

    // 统一处理返回数据
    const result = response.data;

    // SUCCESS(0, "成功"), // 成功
    if (result.code === 0) {
      return result.data;
    }
    // USER_NOT_LOGIN(300, "用户未登录"),
    // TOKEN_EXPIRED(301, "token已过期"),
    if (result.code === 300 || result.code === 301) {
      // 移除token
      useToken().value = undefined;
      // TODO 展示登录框
      return;
    }
    ElMessage({
      message: result.message,
      type: "error"
    });
  },
  error => {
    return Promise.reject(error);
  }
);

export default service;
