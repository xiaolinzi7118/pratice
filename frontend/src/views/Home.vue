<template>
  <div class="home-page">
    <el-card class="welcome-card">
      <div class="welcome-content">
        <h1 class="welcome-title">欢迎使用管理系统</h1>
        <p class="welcome-subtitle">欢迎回来，{{ authStore.user?.name || '用户' }}！</p>
        <div class="welcome-info">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-card shadow="hover" class="info-card">
                <div class="info-item">
                  <el-icon class="info-icon" :size="40" color="#409EFF">
                    <Document />
                  </el-icon>
                  <div class="info-text">
                    <div class="info-label">待办事项</div>
                    <div class="info-value">管理您的任务</div>
                  </div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="hover" class="info-card">
                <div class="info-item">
                  <el-icon class="info-icon" :size="40" color="#67C23A">
                    <Check />
                  </el-icon>
                  <div class="info-text">
                    <div class="info-label">任务完成</div>
                    <div class="info-value">跟踪进度</div>
                  </div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="hover" class="info-card">
                <div class="info-item">
                  <el-icon class="info-icon" :size="40" color="#E6A23C">
                    <Clock />
                  </el-icon>
                  <div class="info-text">
                    <div class="info-label">时间管理</div>
                    <div class="info-value">提高效率</div>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
        <div class="quick-actions">
          <el-button type="primary" size="large" @click="goToTodos">
            <el-icon><List /></el-icon>
            前往待办事项
          </el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { Document, Check, Clock, List } from '@element-plus/icons-vue'

const router = useRouter()
const authStore = useAuthStore()

const goToTodos = () => {
  router.push('/todos')
}
</script>

<style scoped>
.home-page {
  width: 100%;
}

.welcome-card {
  min-height: 500px;
}

.welcome-content {
  text-align: center;
  padding: 40px 20px;
}

.welcome-title {
  font-size: 32px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 16px;
}

.welcome-subtitle {
  font-size: 18px;
  color: #606266;
  margin-bottom: 40px;
}

.welcome-info {
  margin: 40px 0;
}

.info-card {
  height: 100%;
  cursor: pointer;
  transition: transform 0.3s;
}

.info-card:hover {
  transform: translateY(-5px);
}

.info-item {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 10px;
}

.info-icon {
  flex-shrink: 0;
}

.info-text {
  text-align: left;
}

.info-label {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 8px;
}

.info-value {
  font-size: 14px;
  color: #909399;
}

.quick-actions {
  margin-top: 40px;
}
</style>

