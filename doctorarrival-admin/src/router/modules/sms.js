import Layout from "@/layout/index.vue";
import { createNameComponent } from "../createNode";
const route = [
  {
    path: "/sms",
    component: Layout,
    redirect: "/sms/index",
    meta: { title: "sms", icon: "iconfont icon-zidian" },
    children: [
      {
        path: "index",
        component: createNameComponent(() => import("@/views/main/sms/index.vue")),
        meta: { title: "消息管理", hideClose: true }
      }
    ]
  }
];

export default route;
