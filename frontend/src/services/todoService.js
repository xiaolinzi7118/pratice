import api from '../utils/request'

export const todoService = {
  // 获取所有任务（支持筛选和排序）
  async getAllTodos(filters = {}) {
    const params = new URLSearchParams()
    
    if (filters.status) params.append('status', filters.status)
    if (filters.priority) params.append('priority', filters.priority)
    if (filters.category) params.append('category', filters.category)
    if (filters.dueDateStart) params.append('dueDateStart', filters.dueDateStart)
    if (filters.dueDateEnd) params.append('dueDateEnd', filters.dueDateEnd)
    if (filters.sortBy) params.append('sortBy', filters.sortBy)
    if (filters.sortOrder) params.append('sortOrder', filters.sortOrder)
    
    const queryString = params.toString()
    const url = queryString ? `/todos?${queryString}` : '/todos'
    const response = await api.get(url)
    return response.data
  },

  // 创建新任务
  async createTodo(todo) {
    const response = await api.post('/todos', todo)
    return response.data
  },

  // 更新任务
  async updateTodo(id, todo) {
    const response = await api.put(`/todos/${id}`, todo)
    return response.data
  },

  // 删除任务
  async deleteTodo(id) {
    await api.delete(`/todos/${id}`)
  },

  // 批量删除任务
  async deleteTodos(ids) {
    await api.post('/todos/batch/delete', { ids })
  },

  // 批量更新任务状态
  async updateTodosStatus(ids, completed) {
    await api.post('/todos/batch/update-status', { ids, completed })
  }
}

