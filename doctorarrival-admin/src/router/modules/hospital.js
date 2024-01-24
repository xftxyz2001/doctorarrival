import Layout from "@/layout/index.vue";
import { createNameComponent } from "../createNode";
const route = [
  {
    path: "/hospital",
    component: Layout,
    redirect: "/hospital/index",
    meta: { title: "hospital", icon: "iconfont icon-yiyuan" },
    children: [
      {
        path: "index",
        component: createNameComponent(() => import("@/views/main/hospital/index.vue")),
        meta: { title: "医院管理", hideClose: true }
      }
    ]
  }
];

export default route;
