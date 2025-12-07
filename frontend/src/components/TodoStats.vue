<template>
  <el-row :gutter="20" class="stats-container">
    <el-col :span="6">
      <el-card shadow="hover" class="stat-card">
        <div class="stat-item">
          <div class="stat-value">{{ stats.total }}</div>
          <div class="stat-label">总任务数</div>
        </div>
        <el-icon class="stat-icon total"><Document /></el-icon>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card shadow="hover" class="stat-card">
        <div class="stat-item">
          <div class="stat-value">{{ stats.completed }}</div>
          <div class="stat-label">已完成</div>
        </div>
        <el-icon class="stat-icon completed"><CircleCheck /></el-icon>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card shadow="hover" class="stat-card">
        <div class="stat-item">
          <div class="stat-value">{{ stats.pending }}</div>
          <div class="stat-label">待完成</div>
        </div>
        <el-icon class="stat-icon pending"><Clock /></el-icon>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card shadow="hover" class="stat-card">
        <div class="stat-item">
          <div class="stat-value">{{ completionRate }}%</div>
          <div class="stat-label">完成率</div>
        </div>
        <el-icon class="stat-icon rate"><DataAnalysis /></el-icon>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { computed } from 'vue'
import { Document, CircleCheck, Clock, DataAnalysis } from '@element-plus/icons-vue'

const props = defineProps({
  todos: {
    type: Array,
    default: () => []
  }
})

const stats = computed(() => {
  // 确保 todos 是数组
  const todosArray = Array.isArray(props.todos) ? props.todos : []
  const total = todosArray.length
  const completed = todosArray.filter(todo => todo.completed).length
  const pending = total - completed
  
  return {
    total,
    completed,
    pending
  }
})

const completionRate = computed(() => {
  if (stats.value.total === 0) return 0
  return Math.round((stats.value.completed / stats.value.total) * 100)
})
</script>

<style scoped>
.stats-container {
  margin-bottom: 20px;
}

.stat-card {
  position: relative;
  overflow: hidden;
}

.stat-item {
  display: flex;
  flex-direction: column;
  z-index: 1;
  position: relative;
}

.stat-value {
  font-size: 32px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.stat-icon {
  position: absolute;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 48px;
  opacity: 0.2;
}

.stat-icon.total {
  color: #409EFF;
}

.stat-icon.completed {
  color: #67C23A;
}

.stat-icon.pending {
  color: #E6A23C;
}

.stat-icon.rate {
  color: #F56C6C;
}
</style>

