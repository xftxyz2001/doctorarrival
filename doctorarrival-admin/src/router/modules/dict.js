import Layout from "@/layout/index.vue";
import { createNameComponent } from "../createNode";
const route = [
  {
    path: "/dict",
    component: Layout,
    redirect: "/dict/index",
    meta: { title: "dict", icon: "iconfont icon-zidian" },
    children: [
      {
        path: "index",
        component: createNameComponent(() => import("@/views/main/dict/index.vue")),
        meta: { title: "数据字典", hideClose: true }
      }
    ]
  }
];

export default route;
