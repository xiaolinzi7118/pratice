<template>
    <div class="login-container">
      <div class="login-box">
        <h2 class="login-title">后台管理系统</h2>
        <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="loginRules"
          class="login-form"
        >
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名"
              size="large"
              :prefix-icon="User"
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              size="large"
              :prefix-icon="Lock"
              @keyup.enter="handleLogin"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              size="large"
              :loading="loading"
              class="login-button"
              @click="handleLogin"
            >
              登录
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, reactive } from 'vue'
  import { useRouter } from 'vue-router'
  import { useAuthStore } from '../stores/auth'
  import { ElMessage } from 'element-plus'
  import { User, Lock } from '@element-plus/icons-vue'
  
  const router = useRouter()
  const authStore = useAuthStore()
  const loginFormRef = ref(null)
  const loading = ref(false)
  
  const loginForm = reactive({
    username: '',
    password: ''
  })
  
  const loginRules = {
    username: [
      { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' }
    ]
  }
  
  const handleLogin = async () => {
    if (!loginFormRef.value) return
    
    await loginFormRef.value.validate(async (valid) => {
      if (valid) {
        loading.value = true
        try {
          const result = await authStore.login(loginForm.username, loginForm.password)
          if (result.success) {
            ElMessage.success(result.message)
            router.push('/home')
          } else {
            ElMessage.error(result.message)
          }
        } catch (error) {
          ElMessage.error('登录失败，请稍后重试')
        } finally {
          loading.value = false
        }
      }
    })
  }
  </script>
  
  <style scoped>
  .login-container {
    width: 100%;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  }
  
  .login-box {
    width: 400px;
    padding: 40px;
    background: white;
    border-radius: 10px;
    box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
  }
  
  .login-title {
    text-align: center;
    margin-bottom: 30px;
    color: #333;
    font-size: 24px;
    font-weight: 600;
  }
  
  .login-form {
    margin-top: 20px;
  }
  
  .login-button {
    width: 100%;
    margin-top: 10px;
  }
  </style>