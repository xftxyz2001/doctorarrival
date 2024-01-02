import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route = [
  {
    path: '/oss',
    component: Layout,
    redirect: '/oss/index',
    meta: { title: 'oss', icon: 'iconfont icon-shujuku' },
    children: [
      {
        path: 'index',
        component: createNameComponent(() => import('@/views/main/oss/index.vue')),
        meta: { title: '对象存储', hideClose: true }
      }
    ]
  }
]

export default route