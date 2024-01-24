import Layout from "@/layout/index.vue";
import { createNameComponent } from "../createNode";
const route = [
  {
    path: "/task",
    component: Layout,
    redirect: "/task/index",
    meta: { title: "task", icon: "iconfont icon-renwu" },
    children: [
      {
        path: "index",
        component: createNameComponent(() => import("@/views/main/task/index.vue")),
        meta: { title: "定时任务", hideClose: true }
      }
    ]
  }
];

export default route;
