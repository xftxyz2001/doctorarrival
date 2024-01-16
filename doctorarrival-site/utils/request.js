import axios from "axios";

const service = axios.create({
  baseURL: "http://localhost",
  timeout: 5000
});

service.interceptors.request.use(
  config => {
    const token = useToken().value;
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
