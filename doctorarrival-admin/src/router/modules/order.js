import Layout from "@/layout/index.vue";
import { createNameComponent } from "../createNode";
const route = [
  {
    path: "/order",
    component: Layout,
    redirect: "/order/index",
    meta: { title: "order", icon: "iconfont icon-dingdan" },
    children: [
      {
        path: "index",
        component: createNameComponent(() => import("@/views/main/order/index.vue")),
        meta: { title: "订单管理", hideClose: true }
      }
    ]
  }
];

export default route;
