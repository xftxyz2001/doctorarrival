<template>
  <div class="header-container">
    <div class="wrapper">
      <!-- logo -->
      <div class="left-wrapper v-link selected" @click="$router.push('/')">
        <img style="width: 50px; height: 50px" src="assets/images/logo.png" />
        <span class="text">“医来” 预约挂号统一平台</span>
      </div>

      <!-- 搜索框 -->
      <div class="search-wrapper" v-show="$route.path !== '/'">
        <div class="hospital-search animation-show">
          <div id="search" style="display: block; width: 100%">
            <hospitalsearcher />
          </div>
        </div>
      </div>

      <!-- 右侧 -->
      <div class="right-wrapper">
        <span class="v-link clickable">帮助中心</span>

        <!-- 未登录状态 -->
        <span v-if="!token" class="v-link clickable" @click="showLoginDialog">登录/注册</span>

        <!-- 登录状态 -->
        <el-dropdown v-if="token" @command="loginUserMenu">
          <span class="el-dropdown-link">
            {{ token.nickName }}
            <el-icon class="el-icon--right"><el-icon-arrow-down /></el-icon>
          </span>
          <template v-slot:dropdown>
            <el-dropdown-menu class="user-name-wrapper">
              <el-dropdown-item command="/user">用户资料</el-dropdown-item>
              <el-dropdown-item command="/patient">就诊人</el-dropdown-item>
              <el-dropdown-item command="/order">挂号订单</el-dropdown-item>
              <el-dropdown-item command="/logout" divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <!-- 登录弹出层 -->
    <el-dialog
      v-model="dialogUserFormVisible"
      style="text-align: left"
      top="50px"
      :append-to-body="true"
      width="960px"
      @close="closeLoginDialog"
    >
      <div class="container">
        <!-- 手机登录 -->
        <div class="operate-view" v-show="dialogAtrr.showLoginType === 'phone'">
          <div class="wrapper" style="width: 100%">
            <div class="mobile-wrapper" style="position: static; width: 70%">
              <span class="title">{{ dialogAtrr.labelTips }}</span>
              <el-form>
                <el-form-item>
                  <el-input
                    v-model="dialogAtrr.inputValue"
                    :placeholder="dialogAtrr.placeholder"
                    :maxlength="dialogAtrr.maxlength"
                    class="input v-input"
                  >
                    <template v-slot:suffix>
                      <span class="sendText v-link" v-if="dialogAtrr.second > 0">{{ dialogAtrr.second }}s</span>
                      <!-- </template>
                    <template v-slot:suffix> -->
                      <span
                        class="sendText v-link highlight clickable selected"
                        v-if="dialogAtrr.second == 0"
                        @click="getVerificationCode"
                      >
                        重新发送
                      </span>
                    </template>
                  </el-input>
                </el-form-item>
              </el-form>
              <div class="send-button v-button" @click="sendButtonClick">
                {{ dialogAtrr.loginBtn }}
              </div>
            </div>
            <div class="bottom">
              <div class="wechat-wrapper" @click="weixinLogin">
                <span class="iconfont icon"></span>
              </div>
              <span class="third-text">第三方账号登录</span>
            </div>
          </div>
        </div>

        <!-- 微信登录 -->
        <div class="operate-view" v-show="dialogAtrr.showLoginType === 'weixin'">
          <div class="wrapper wechat" style="height: 400px">
            <div>
              <div id="wxlogin_container">正在加载微信登录二维码...</div>
            </div>
            <div class="bottom wechat" style="margin-top: -80px">
              <div class="phone-container">
                <div class="phone-wrapper" @click="phoneLogin()">
                  <span class="iconfont icon"></span>
                </div>
                <span class="third-text">手机短信验证码登录</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 推广信息 -->
        <div class="info-wrapper">
          <div class="code-wrapper">
            <div>
              <img src="assets/images/code_wechat.png" class="code-img" />
              <div class="code-text">
                <span class="iconfont icon"></span>
                微信扫一扫关注
              </div>
              <div class="code-text">“快速预约挂号”</div>
            </div>
            <div class="wechat-code-wrapper">
              <img src="assets/images/code_app.png" class="code-img" />
              <div class="code-text">扫一扫下载</div>
              <div class="code-text">“预约挂号”APP</div>
            </div>
          </div>
          <div class="slogan">
            <div>xxxxxx官方指定平台</div>
            <div>快速挂号 安全放心</div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { sendVerificationCode } from "@/api/sms";
import { login, getUserInfoBasic, getWxLoginQrCodeParam } from "@/api/user";

const router = useRouter();

// 登录弹出层默认属性值
const defaultDialogAtrr = {
  showLoginType: "phone", // 控制手机登录（phone）与微信登录（weixin）切换

  labelTips: "手机号码", // 输入框提示

  inputValue: "", // 输入框绑定对象
  placeholder: "请输入您的手机号", // 输入框placeholder
  maxlength: 11, // 输入框长度控制

  loginBtn: "获取验证码", // 登录按钮或获取验证码按钮文本

  second: -1 // 倒计时间  second>0 : 显示倒计时 second=0 ：重新发送 second=-1 ：什么都不显示
};

const dialogUserFormVisible = useLoginDialogVisible(); // 登录弹出层
const token = useToken(); // 昵称
const dialogAtrr = ref(JSON.parse(JSON.stringify(defaultDialogAtrr))); // 登录弹出层属性
let clearSmsTime = null; // 倒计时定时任务引用 关闭登录层清除定时任务
let phoneNumber = ""; // 上次发送验证码的手机号（用于重新发送验证码）

// 初始化登录弹出层属性
function initDialogAtrr() {
  dialogAtrr.value = JSON.parse(JSON.stringify(defaultDialogAtrr));
}

// 点击登录/注册
function showLoginDialog() {
  initDialogAtrr();
  dialogUserFormVisible.value = true;
}

// 关闭登录弹出层
function closeLoginDialog() {
  if (clearSmsTime) {
    clearInterval(clearSmsTime);
  }
  dialogUserFormVisible.value = false;
}

// 用户菜单
function loginUserMenu(command) {
  if (command === "/user") {
    router.push("/user/info");
  } else if (command === "/patient") {
    router.push("/user/patient");
  } else if (command === "/order") {
    router.push("/user/order");
  } else if (command === "/logout") {
    // 清除token
    token.value = undefined;
    // 清除昵称
    // nickName.value = "";
    location.reload();
    // router.push("/");
    // router.push({
    //   path: "/",
    //   query: {
    //     s: Math.random()
    //   }
    // });
  }
}

// 检查手机号合法性
function checkPhone(phone) {
  if (!phone) {
    ElMessage({
      message: "请输入手机号",
      type: "warning"
    });
    return false;
  }
  if (!/^1[3-9]\d{9}$/.test(phone)) {
    ElMessage({
      message: "手机号格式不正确",
      type: "warning"
    });
    return false;
  }
  return true;
}

// 倒计时
function countDown() {
  if (clearSmsTime) {
    clearInterval(clearSmsTime);
  }
  dialogAtrr.value.second = 60; // 下次发送倒计时
  clearSmsTime = setInterval(() => {
    dialogAtrr.value.second--;
    if (dialogAtrr.value.second <= 0) {
      clearInterval(clearSmsTime);
      dialogAtrr.value.second = 0;
    }
  }, 1000);
}

// 获取验证码
function getVerificationCode() {
  if (dialogAtrr.value.loginBtn === "获取验证码") {
    phoneNumber = dialogAtrr.value.inputValue;
  }
  // 检查手机号合法性
  if (!checkPhone(phoneNumber)) {
    return;
  }
  // 控制重复发送
  if (dialogAtrr.value.second > 0) {
    return;
  }
  countDown(); // 倒计时
  // 初始化验证码相关属性
  dialogAtrr.value.inputValue = "";
  dialogAtrr.value.placeholder = "请输入验证码";
  dialogAtrr.value.maxlength = 6;
  dialogAtrr.value.loginBtn = "马上登录";

  // 发送验证码
  sendVerificationCode(phoneNumber);
  dialogAtrr.value.labelTips = "验证码已发送至" + phoneNumber;
  // sendVerificationCode(phoneNumber).then(res => {
  //   // false：发送失败
  //   if (!res) {
  //     ElMessage({
  //       message: '发送失败，请稍后重试',
  //       type: 'error'
  //     })
  //     return
  //   }
  //   // true：发送成功
  //   dialogAtrr.value.labelTips = '验证码已发送至' + phoneNumber
  // })
}

// 尝试获取昵称
// function tryGetNickName() {
//   // 检查token
//   if (!useToken().value) {
//     return;
//   }
//   getUserInfoBasic().then(res => {
//     nickName.value = res.nickName;
//   });
// }
// tryGetNickName();

// 登陆成功
function loginSuccess(loginResponse) {
  // 保存token
  token.value = loginResponse;
  // 获取用户基本信息
  // tryGetNickName();
  // 关闭登录弹出层
  closeLoginDialog();
}

// 点击发送验证码或登录按钮
function sendButtonClick() {
  if (dialogAtrr.value.loginBtn === "获取验证码") {
    getVerificationCode();
  } else if (dialogAtrr.value.loginBtn === "马上登录") {
    const verificationCode = dialogAtrr.value.inputValue;
    if (!verificationCode) {
      ElMessage({
        message: "请输入验证码",
        type: "warning"
      });
      return;
    } else if (verificationCode.length !== dialogAtrr.value.maxlength) {
      ElMessage({
        message: "验证码位数不正确",
        type: "warning"
      });
      return;
    }
    // 执行验证码登录
    login({ phoneNumber, verificationCode }).then(res => {
      if (res) {
        loginSuccess(res);
      } else {
        // 登录失败
        ElMessage({
          message: "登录失败",
          type: "error"
        });
      }
    });
  }
}

// 微信登录
function weixinLogin() {
  dialogAtrr.value.showLoginType = "weixin";
  getWxLoginQrCodeParam().then(res => {
    const obj = new WxLogin(res);
  });
}

// 手机号登录
function phoneLogin() {
  dialogAtrr.value.showLoginType = "phone";
}

// 监听微信扫码回调
// if (window) {
//   addEventListener("message", e => {
//     if (e.origin === location.origin) {
//       loginSuccess(e.data);
//     }
//   });
// }
</script>
