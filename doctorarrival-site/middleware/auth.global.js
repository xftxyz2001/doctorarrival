function needAuth(path) {
  return path.includes("user") || path.includes("booking") || path.includes("order");
}

export default defineNuxtRouteMiddleware((to, from) => {
  if (needAuth(to.path)) {
    // 没有token
    if (process.client && !useToken().value) {
      // 键入网址/刷新页面进入，导向首页
      if (needAuth(from.path)) {
        return navigateTo("/");
      }
      // 其他情况，弹出登录框
      useLoginDialogVisible().value = true;
      return abortNavigation();
    }
  }
});
