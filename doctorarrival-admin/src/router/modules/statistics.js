import Layout from "@/layout/index.vue";
import { createNameComponent } from "../createNode";
const route = [
  {
    path: "/statistics",
    component: Layout,
    redirect: "/statistics/index",
    meta: { title: "statistics", icon: "iconfont icon-tongji" },
    children: [
      {
        path: "index",
        component: createNameComponent(() => import("@/views/main/statistics/index.vue")),
        meta: { title: "统计分析", hideClose: true }
      }
    ]
  }
];

export default route;
