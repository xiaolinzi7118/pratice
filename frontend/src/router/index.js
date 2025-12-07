import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('../views/Login.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/',
      component: () => import('../layouts/AdminLayout.vue'),
      redirect: '/home',
      meta: { requiresAuth: true },
      children: [
        {
          path: 'home',
          name: 'Home',
          component: () => import('../views/Home.vue'),
          meta: { 
            title: '首页',
            breadcrumb: [{ title: '首页', path: '/home' }]
          }
        },
        {
          path: 'todos',
          name: 'Todos',
          component: () => import('../views/Todos.vue'),
          meta: { 
            title: '待办事项',
            breadcrumb: [
              { title: '首页', path: '/home' },
              { title: '待办事项', path: '/todos' }
            ]
          }
        },
        {
          path: 'h5/flash-news',
          name: 'FlashNews',
          component: () => import('../views/FlashNews.vue'),
          meta: { 
            title: '快讯',
            breadcrumb: [
              { title: '首页', path: '/home' },
              { title: 'H5页面', path: '/h5' },
              { title: '快讯', path: '/h5/flash-news' }
            ]
          }
        }
      ]
    }
  ]
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  // 初始化认证状态
  authStore.initAuth()
  
  // 如果路由需要认证
  if (to.meta.requiresAuth) {
    if (authStore.isAuthenticated) {
      next()
    } else {
      next('/login')
    }
  } else {
    // 如果已登录，访问登录页则重定向到首页
    if (to.path === '/login' && authStore.isAuthenticated) {
      next('/')
    } else {
      next()
    }
  }
})

export default router