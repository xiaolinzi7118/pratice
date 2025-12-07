<template>
  <div class="todos-page">
    <!-- 统计面板 -->
    <TodoStats :todos="todos" />

    <el-card class="todos-card">
      <template #header>
        <div class="card-header">
          <span class="card-title">待办事项管理</span>
        </div>
      </template>

      <!-- 工具栏 -->
      <div class="toolbar">
        <div class="toolbar-left">
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            添加任务
          </el-button>
          <el-button
            v-if="selectedRows.length > 0"
            type="success"
            @click="handleBatchComplete"
          >
            <el-icon><CircleCheck /></el-icon>
            批量标记完成 ({{ selectedRows.length }})
          </el-button>
          <el-button
            v-if="selectedRows.length > 0"
            type="danger"
            @click="handleBatchDelete"
          >
            <el-icon><Delete /></el-icon>
            批量删除 ({{ selectedRows.length }})
          </el-button>
        </div>
        <div class="toolbar-right">
          <el-input
            v-model="searchText"
            placeholder="搜索任务..."
            clearable
            style="width: 300px;"
            @input="handleSearch"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
        </div>
      </div>

      <!-- 筛选栏 -->
      <div class="filter-bar">
        <el-select
          v-model="filters.status"
          placeholder="状态"
          clearable
          style="width: 120px; margin-right: 10px;"
          @change="handleFilterChange"
        >
          <el-option label="全部" value="" />
          <el-option label="已完成" value="completed" />
          <el-option label="待完成" value="pending" />
        </el-select>
        <el-select
          v-model="filters.priority"
          placeholder="优先级"
          clearable
          style="width: 120px; margin-right: 10px;"
          @change="handleFilterChange"
        >
          <el-option label="全部" value="" />
          <el-option label="高" value="HIGH" />
          <el-option label="中" value="MEDIUM" />
          <el-option label="低" value="LOW" />
        </el-select>
        <el-input
          v-model="filters.category"
          placeholder="分类"
          clearable
          style="width: 150px; margin-right: 10px;"
          @clear="handleFilterChange"
          @keyup.enter="handleFilterChange"
        />
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DDTHH:mm:ss"
          style="width: 250px; margin-right: 10px;"
          @change="handleDateRangeChange"
        />
        <el-button @click="handleResetFilter">重置筛选</el-button>
      </div>

      <!-- 表格 -->
      <el-table
        v-loading="loading"
        :data="filteredTodos"
        stripe
        style="width: 100%; margin-top: 20px;"
        empty-text="暂无数据"
        @selection-change="handleSelectionChange"
        @sort-change="handleSortChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" sortable="custom" />
        <el-table-column prop="title" label="任务标题" min-width="200" sortable="custom" />
        <el-table-column label="优先级" width="100" align="center" sortable="custom" prop="priority">
          <template #default="{ row }">
            <el-tag
              v-if="row.priority"
              :type="getPriorityType(row.priority)"
              size="small"
            >
              {{ getPriorityText(row.priority) }}
            </el-tag>
            <span v-else class="text-placeholder">-</span>
          </template>
        </el-table-column>
        <el-table-column label="分类" width="120">
          <template #default="{ row }">
            <el-tag v-if="row.category" size="small" type="info">
              {{ row.category }}
            </el-tag>
            <span v-else class="text-placeholder">-</span>
          </template>
        </el-table-column>
        <el-table-column label="截止日期" width="160" sortable="custom" prop="dueDate">
          <template #default="{ row }">
            <span v-if="row.dueDate" :class="{ 'overdue': isOverdue(row.dueDate, row.completed) }">
              {{ formatDate(row.dueDate) }}
            </span>
            <span v-else class="text-placeholder">-</span>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="创建时间" width="160" sortable="custom">
          <template #default="{ row }">
            {{ formatDate(row.createdAt) }}
          </template>
        </el-table-column>
        <el-table-column label="完成状态" width="120" align="center">
          <template #default="{ row }">
            <el-switch
              v-model="row.completed"
              @change="handleToggleComplete(row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center" fixed="right">
          <template #default="{ row }">
            <el-button
              type="primary"
              link
              size="small"
              @click="handleEdit(row)"
            >
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button
              type="danger"
              link
              size="small"
              @click="handleDelete(row.id)"
            >
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      @close="handleDialogClose"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="任务标题" prop="title">
          <el-input
            v-model="formData.title"
            placeholder="请输入任务标题"
            maxlength="200"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="优先级">
          <el-radio-group v-model="formData.priority">
            <el-radio label="HIGH">高</el-radio>
            <el-radio label="MEDIUM">中</el-radio>
            <el-radio label="LOW">低</el-radio>
            <el-radio :label="null">无</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="分类">
          <el-input
            v-model="formData.category"
            placeholder="请输入分类（如：工作、生活、学习等）"
            clearable
          />
        </el-form-item>
        <el-form-item label="截止日期">
          <el-date-picker
            v-model="formData.dueDate"
            type="datetime"
            placeholder="选择截止日期"
            format="YYYY-MM-DD HH:mm"
            value-format="YYYY-MM-DDTHH:mm:ss"
            style="width: 100%;"
          />
        </el-form-item>
        <el-form-item label="任务描述">
          <el-input
            v-model="formData.description"
            type="textarea"
            :rows="4"
            placeholder="请输入任务描述..."
            maxlength="1000"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="完成状态">
          <el-switch v-model="formData.completed" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="submitting">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Search, Edit, Delete, CircleCheck } from '@element-plus/icons-vue'
import { todoService } from '../services/todoService'
import TodoStats from '../components/TodoStats.vue'

const todos = ref([])
const loading = ref(false)
const searchText = ref('')
const dialogVisible = ref(false)
const dialogTitle = ref('添加任务')
const submitting = ref(false)
const formRef = ref(null)
const isEdit = ref(false)
const currentEditId = ref(null)
const selectedRows = ref([])
const dateRange = ref(null)

const filters = ref({
  status: '',
  priority: '',
  category: '',
  dueDateStart: null,
  dueDateEnd: null
})

const sortOptions = ref({
  sortBy: '',
  sortOrder: ''
})

const formData = ref({
  title: '',
  completed: false,
  priority: null,
  category: '',
  dueDate: null,
  description: ''
})

const formRules = {
  title: [
    { required: true, message: '请输入任务标题', trigger: 'blur' },
    { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
  ]
}

// 优先级映射
const getPriorityText = (priority) => {
  const map = {
    'HIGH': '高',
    'MEDIUM': '中',
    'LOW': '低'
  }
  return map[priority] || priority
}

const getPriorityType = (priority) => {
  const map = {
    'HIGH': 'danger',
    'MEDIUM': 'warning',
    'LOW': 'info'
  }
  return map[priority] || ''
}

// 日期格式化
const formatDate = (dateString) => {
  if (!dateString) return '-'
  const date = new Date(dateString)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 判断是否过期
const isOverdue = (dueDate, completed) => {
  if (!dueDate || completed) return false
  return new Date(dueDate) < new Date()
}

// 过滤后的待办事项（前端筛选，如果需要后端筛选可以调用API）
const filteredTodos = computed(() => {
  let result = todos.value

  // 文本搜索
  if (searchText.value) {
    const search = searchText.value.toLowerCase()
    result = result.filter(todo =>
      todo.title.toLowerCase().includes(search) ||
      (todo.description && todo.description.toLowerCase().includes(search)) ||
      (todo.category && todo.category.toLowerCase().includes(search))
    )
  }

  return result
})

// 加载所有任务
const loadTodos = async () => {
  try {
    loading.value = true
    const filterParams = {
      ...filters.value,
      ...sortOptions.value
    }
    // 清理空值
    Object.keys(filterParams).forEach(key => {
      if (filterParams[key] === '' || filterParams[key] === null) {
        delete filterParams[key]
      }
    })
    const result = await todoService.getAllTodos(filterParams)
    // 确保返回的是数组
    todos.value = Array.isArray(result) ? result : []
  } catch (error) {
    console.error('加载任务失败:', error)
    ElMessage.error('加载任务失败，请稍后重试')
    // 确保错误时也是数组
    todos.value = []
  } finally {
    loading.value = false
  }
}

// 搜索处理
const handleSearch = () => {
  // 搜索逻辑已在 computed 中处理
}

// 筛选变化
const handleFilterChange = () => {
  loadTodos()
}

// 日期范围变化
const handleDateRangeChange = (dates) => {
  if (dates && dates.length === 2) {
    filters.value.dueDateStart = dates[0]
    filters.value.dueDateEnd = dates[1] + 'T23:59:59'
  } else {
    filters.value.dueDateStart = null
    filters.value.dueDateEnd = null
  }
  handleFilterChange()
}

// 重置筛选
const handleResetFilter = () => {
  filters.value = {
    status: '',
    priority: '',
    category: '',
    dueDateStart: null,
    dueDateEnd: null
  }
  dateRange.value = null
  searchText.value = ''
  sortOptions.value = { sortBy: '', sortOrder: '' }
  loadTodos()
}

// 排序变化
const handleSortChange = ({ prop, order }) => {
  if (order) {
    sortOptions.value.sortBy = prop
    sortOptions.value.sortOrder = order === 'ascending' ? 'asc' : 'desc'
  } else {
    sortOptions.value.sortBy = ''
    sortOptions.value.sortOrder = ''
  }
  loadTodos()
}

// 选择变化
const handleSelectionChange = (selection) => {
  selectedRows.value = selection.map(row => row.id)
}

// 打开添加对话框
const handleAdd = () => {
  isEdit.value = false
  currentEditId.value = null
  dialogTitle.value = '添加任务'
  formData.value = {
    title: '',
    completed: false,
    priority: null,
    category: '',
    dueDate: null,
    description: ''
  }
  dialogVisible.value = true
}

// 打开编辑对话框
const handleEdit = (row) => {
  isEdit.value = true
  currentEditId.value = row.id
  dialogTitle.value = '编辑任务'
  formData.value = {
    title: row.title,
    completed: row.completed || false,
    priority: row.priority || null,
    category: row.category || '',
    dueDate: row.dueDate || null,
    description: row.description || ''
  }
  dialogVisible.value = true
}

// 对话框关闭处理
const handleDialogClose = () => {
  formRef.value?.resetFields()
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        const todoData = {
          title: formData.value.title,
          completed: formData.value.completed,
          priority: formData.value.priority || null,
          category: formData.value.category || null,
          dueDate: formData.value.dueDate || null,
          description: formData.value.description || null
        }

        if (isEdit.value) {
          // 编辑
          const updatedTodo = await todoService.updateTodo(currentEditId.value, todoData)
          const index = todos.value.findIndex(t => t.id === currentEditId.value)
          if (index !== -1) {
            todos.value[index] = updatedTodo
          }
          ElMessage.success('任务更新成功')
        } else {
          // 添加
          const newTodo = await todoService.createTodo(todoData)
          todos.value.push(newTodo)
          ElMessage.success('任务添加成功')
        }
        dialogVisible.value = false
        loadTodos() // 重新加载以获取完整数据
      } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error(isEdit.value ? '更新任务失败，请稍后重试' : '添加任务失败，请稍后重试')
      } finally {
        submitting.value = false
      }
    }
  })
}

// 切换任务完成状态
const handleToggleComplete = async (row) => {
  try {
    const updatedTodo = await todoService.updateTodo(row.id, {
      title: row.title,
      completed: row.completed,
      priority: row.priority,
      category: row.category,
      dueDate: row.dueDate,
      description: row.description
    })
    const index = todos.value.findIndex(t => t.id === row.id)
    if (index !== -1) {
      todos.value[index] = updatedTodo
    }
    ElMessage.success(row.completed ? '任务标记为已完成' : '任务标记为未完成')
  } catch (error) {
    // 恢复状态
    row.completed = !row.completed
    console.error('更新任务失败:', error)
    ElMessage.error('更新任务失败，请稍后重试')
  }
}

// 删除任务
const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个任务吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await todoService.deleteTodo(id)
    todos.value = todos.value.filter(t => t.id !== id)
    ElMessage.success('任务删除成功')
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除任务失败:', error)
      ElMessage.error('删除任务失败，请稍后重试')
    }
  }
}

// 批量标记完成
const handleBatchComplete = async () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请选择要操作的任务')
    return
  }
  try {
    await todoService.updateTodosStatus(selectedRows.value, true)
    ElMessage.success('批量标记完成成功')
    selectedRows.value = []
    loadTodos()
  } catch (error) {
    console.error('批量操作失败:', error)
    ElMessage.error('批量操作失败，请稍后重试')
  }
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请选择要删除的任务')
    return
  }
  try {
    await ElMessageBox.confirm(`确定要删除选中的 ${selectedRows.value.length} 个任务吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await todoService.deleteTodos(selectedRows.value)
    ElMessage.success('批量删除成功')
    selectedRows.value = []
    loadTodos()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除失败:', error)
      ElMessage.error('批量删除失败，请稍后重试')
    }
  }
}

// 组件挂载时加载任务
onMounted(() => {
  loadTodos()
})
</script>

<style scoped>
.todos-page {
  width: 100%;
}

.todos-card {
  min-height: 500px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.toolbar-left {
  display: flex;
  gap: 10px;
}

.toolbar-right {
  display: flex;
  gap: 10px;
}

.filter-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.text-placeholder {
  color: #c0c4cc;
}

.overdue {
  color: #f56c6c;
  font-weight: 600;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
