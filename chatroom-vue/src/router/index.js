import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login'
import Register from '@/views/Register'
import ChatRoom from '@/views/ChatRoom'
import Layout from '@/layout/Layout'
import Person from '@/views/Person'
import User from '@/views/User'
import Permission from '@/views/Permission'
import Role from '@/views/Role'
Vue.use(VueRouter)

const routes = [
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  {
    path: '/',
    component: Layout,
    redirect: '/chatroom',
    // 嵌套路由
    children: [
      { // 当 /chatroom 匹配成功，
        // Chatroom 会被渲染在 Layout 的 <router-view> 中
        path: '/chatroom', component: ChatRoom
      },
      { path: '/user', component: User },
      { path: '/person', component: Person },
      { path: '/permission', component: Permission },
      { path: '/role', component: Role }
    ]
  }
]

const router = new VueRouter({
  routes
})
// 路由导航守卫
router.beforeEach((to, from, next) => {
  if (to.path === '/login') return next()
  if (to.path === '/register') return next()
  const user = sessionStorage.getItem('user')
  if (!user) {
    next('/login')
  } else {
    next()
  }
})
export default router
