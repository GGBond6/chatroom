import Layout from '@/layout/Layout'
import router from '@/router/index'

export function dynamicRouter () {
  const userStr = sessionStorage.getItem('user')
  const root = {
    path: '/',
    component: Layout,
    redirect: '/chatroom',
    // 嵌套路由
    children: []
  }
  if (userStr !== null) {
    const user = JSON.parse(userStr)
    user.permissions.forEach(p => {
      root.children.push({ path: p.path, component: () => import('@/views' + p.path) })
    })
    router.addRoute(root)
  }
}
