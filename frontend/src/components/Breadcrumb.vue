<template>
  <el-breadcrumb separator="/" class="breadcrumb-container">
    <el-breadcrumb-item
      v-for="(item, index) in breadcrumbItems"
      :key="index"
      :to="item.path"
    >
      <el-icon v-if="item.icon" style="margin-right: 4px;">
        <component :is="item.icon" />
      </el-icon>
      {{ item.title }}
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { House, List, Iphone, Bell } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

// 路由路径映射
const routeMap = {
  '/home': { title: '首页', icon: House },
  '/todos': { title: '待办事项', icon: List },
  '/h5': { title: 'H5页面', icon: Iphone },
  '/h5/flash-news': { title: '快讯', icon: Bell }
}

// 生成面包屑项
const breadcrumbItems = computed(() => {
  const items = []
  const pathArray = route.path.split('/').filter(Boolean)
  
  // 如果路径为空，说明在根路径，添加首页
  if (pathArray.length === 0) {
    items.push({
      title: '首页',
      path: '/home',
      icon: House
    })
    return items
  }
  
  // 构建路径层级
  let currentPath = ''
  pathArray.forEach((path, index) => {
    currentPath += '/' + path
    const routeInfo = routeMap[currentPath]
    
    if (routeInfo) {
      items.push({
        title: routeInfo.title,
        path: currentPath,
        icon: routeInfo.icon
      })
    } else {
      // 如果没有映射，使用路由meta中的title或路径名
      const matchedRoute = route.matched.find(r => {
        // 处理动态路由匹配
        const routePath = r.path.replace(/\/:.*$/, '')
        return routePath === currentPath || r.path === currentPath
      })
      items.push({
        title: matchedRoute?.meta?.title || path,
        path: currentPath,
        icon: null
      })
    }
  })
  
  return items
})
</script>

<style scoped>
.breadcrumb-container {
  margin-bottom: 0;
  padding: 0 4px;
}
</style>

