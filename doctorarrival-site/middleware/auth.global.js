export default defineNuxtRouteMiddleware((to, from) => {
  // 如果to.path包含user或booking
  if (to.path.includes("user") || to.path.includes("booking")) {
    // 没有token
    if (!useToken().value) {
      // 键入网址/刷新页面进入，导向首页
      if (from.path.includes("user") || from.path.includes("booking")) {
        return navigateTo("/");
      }
      // 其他情况，弹出登录框
      useLoginDialogVisible().value = true;
      return abortNavigation();
    }
  }
});
