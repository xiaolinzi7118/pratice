import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const isAuthenticated = ref(false)
  const user = ref(null)

  // Mock 登录函数
  const login = async (username, password) => {
    // 模拟登录延迟
    await new Promise(resolve => setTimeout(resolve, 500))
    
    // Mock 登录逻辑：任何用户名和密码都可以登录
    if (username && password) {
      isAuthenticated.value = true
      user.value = {
        username: username,
        name: username === 'admin' ? '管理员' : '用户'
      }
      // 保存到 localStorage
      localStorage.setItem('isAuthenticated', 'true')
      localStorage.setItem('user', JSON.stringify(user.value))
      return { success: true, message: '登录成功' }
    } else {
      return { success: false, message: '用户名和密码不能为空' }
    }
  }

  // 登出函数
  const logout = () => {
    isAuthenticated.value = false
    user.value = null
    localStorage.removeItem('isAuthenticated')
    localStorage.removeItem('user')
  }

  // 初始化：从 localStorage 恢复登录状态
  const initAuth = () => {
    const savedAuth = localStorage.getItem('isAuthenticated')
    const savedUser = localStorage.getItem('user')
    if (savedAuth === 'true' && savedUser) {
      isAuthenticated.value = true
      user.value = JSON.parse(savedUser)
    }
  }

  return {
    isAuthenticated,
    user,
    login,
    logout,
    initAuth
  }
})