<template>
    <el-container class="admin-container">
      <el-aside width="200px" class="admin-aside">
        <div class="logo">
          <h3>管理系统</h3>
        </div>
        <el-menu
          :default-active="activeMenu"
          router
          class="admin-menu"
          background-color="#304156"
          text-color="#bfcbd9"
          active-text-color="#409EFF"
        >
          <el-menu-item index="/home">
            <el-icon><House /></el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-menu-item index="/todos">
            <el-icon><List /></el-icon>
            <span>待办事项</span>
          </el-menu-item>
          <el-sub-menu index="/h5">
            <template #title>
              <el-icon><Iphone /></el-icon>
              <span>H5页面</span>
            </template>
            <el-menu-item index="/h5/flash-news">
              <el-icon><Bell /></el-icon>
              <span>快讯</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header class="admin-header">
          <div class="header-left">
            <span class="header-title">{{ currentTitle }}</span>
          </div>
          <div class="header-right">
            <el-dropdown @command="handleCommand">
              <span class="user-info">
                <el-icon><User /></el-icon>
                <span>{{ authStore.user?.name || '用户' }}</span>
                <el-icon class="el-icon--right"><ArrowDown /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        <div class="breadcrumb-wrapper">
          <Breadcrumb />
        </div>
        <el-main class="admin-main">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </template>
  
  <script setup>
  import { computed } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { useAuthStore } from '../stores/auth'
  import { ElMessageBox } from 'element-plus'
  import { List, User, ArrowDown, House, Iphone, Bell } from '@element-plus/icons-vue'
import Breadcrumb from '../components/Breadcrumb.vue'
  
  const route = useRoute()
  const router = useRouter()
  const authStore = useAuthStore()
  
  const activeMenu = computed(() => {
    return route.path
  })
  
  const currentTitle = computed(() => {
    return route.meta.title || '管理系统'
  })
  
  const handleCommand = async (command) => {
    if (command === 'logout') {
      try {
        await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        authStore.logout()
        router.push('/login')
      } catch {
        // 用户取消
      }
    }
  }
  </script>
  
  <style scoped>
  .admin-container {
    height: 100vh;
  }
  
  .admin-aside {
    background-color: #304156;
    overflow: hidden;
  }
  
  .logo {
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #2b3a4a;
    color: white;
  }
  
  .logo h3 {
    margin: 0;
    font-size: 18px;
    font-weight: 600;
  }
  
  .admin-menu {
    border-right: none;
    height: calc(100vh - 60px);
  }
  
  .admin-header {
    background-color: #fff;
    border-bottom: 1px solid #e4e7ed;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 20px;
  }
  
  .header-left {
    flex: 1;
  }
  
  .header-title {
    font-size: 18px;
    font-weight: 600;
    color: #303133;
  }
  
  .header-right {
    display: flex;
    align-items: center;
  }
  
  .user-info {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    color: #606266;
    padding: 0 12px;
    height: 40px;
    line-height: 40px;
    border-radius: 4px;
    transition: background-color 0.3s;
  }
  
  .user-info:hover {
    background-color: #f5f7fa;
  }
  
  .breadcrumb-wrapper {
    background-color: #fff;
    padding: 8px 20px;
    border-bottom: 1px solid #e4e7ed;
  }

  .admin-main {
    background-color: #f0f2f5;
    padding: 20px;
    overflow-y: auto;
  }
  </style>