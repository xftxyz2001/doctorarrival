/**
 * 前端路由管理
 **/

/** 引入需要权限的Modules */
import Dashboard from '../modules/dashboard'

import Dict from '../modules/dict'
import Hospital from '../modules/hospital'
import User from '../modules/user'

import SMS from '../modules/sms'
import OSS from '../modules/oss'
import Order from '../modules/order'
import Task from '../modules/task'
import Statistics from '../modules/statistics'


/** 登录后需要动态加入的本地路由 */
const FrontRoutes = [
  ...Dashboard,
  ...Dict,
  ...Hospital,
  ...User,
  ...SMS,
  ...OSS,
  ...Order,
  ...Task,
  ...Statistics
]

export default FrontRoutes