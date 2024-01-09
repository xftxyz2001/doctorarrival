// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  app: {
    head: {
      script: [
        // http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js
        { src: "http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js" },
      ]
    }
  },
  css: [
    "assets/css/app.css",
    "assets/css/chunk.css",
    "assets/css/hospital_personal.css",
    "assets/css/hospital.css",
    "assets/css/iconfont.css",
    "assets/css/index.css",
    "assets/css/main.css",
    "assets/css/personal.css",
    "assets/css/reset.css",
  ],
  devtools: { enabled: true },
  modules: [
    '@element-plus/nuxt'
  ],
})
