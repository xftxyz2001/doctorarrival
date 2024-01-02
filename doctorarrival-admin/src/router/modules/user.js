import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route = [
  {
    path: '/user',
    component: Layout,
    redirect: '/user/index',
    meta: { title: 'user', icon: 'iconfont icon-yonghuguanli_huaban' },
    children: [
      {
        path: 'index',
        component: createNameComponent(() => import('@/views/main/user/index.vue')),
        meta: { title: '用户管理', hideClose: true }
      }
    ]
  }
]

export default route