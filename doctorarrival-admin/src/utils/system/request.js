import axios, { AxiosError, AxiosRequestConfig, AxiosResponse, AxiosInstance } from "axios";
import store from "@/store";
import { ElMessage } from "element-plus";
// const baseURL = import.meta.env.VITE_BASE_URL
const baseURL = `http://${window.location.hostname}`;

const service = axios.create({
  baseURL: baseURL,
  timeout: 5000
});

// 请求前的统一处理
service.interceptors.request.use(
  config => {
    // JWT鉴权处理
    if (store.getters["user/token"]) {
      config.headers["Authorization"] = store.state.user.token;
    }
    return config;
  },
  error => {
    console.log(error); // for debug
    return Promise.reject(error);
  }
);

service.interceptors.response.use(
  response => {
    if (response.headers["content-type"].indexOf("application/json") === -1) {
      return response;
    }
    // 统一返回处理
    const res = response.data;
    if (res.code === 0) {
      return res.data;
    } else {
      showError(res);
      return Promise.reject(res);
    }
  },
  error => {
    console.log(error); // for debug
    const badMessage = error.message || error;
    const code = parseInt(badMessage.toString().replace("Error: Request failed with status code ", ""));
    showError({ code, message: badMessage });
    return Promise.reject(error);
  }
);

// 错误处理
function showError(error) {
  // token过期，清除本地数据，并跳转至登录页面
  if (error.code === 301) {
    // to re-login
    store.dispatch("user/loginOut");
  }
  // else {
  //   ElMessage({
  //     message: error.msg || error.message || '服务异常',
  //     type: 'error',
  //     duration: 3 * 1000
  //   })
  // }

  ElMessage({
    message: error.msg || error.message || "服务异常",
    type: "error",
    duration: 3 * 1000
  });
}

export default service;
