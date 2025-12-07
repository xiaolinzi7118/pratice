<template>
  <div class="todo-list">
    <div v-if="todos.length === 0" class="empty-state">
      <p>暂无任务，添加一个开始吧！</p>
    </div>
    <TodoItem
      v-for="todo in todos"
      :key="todo.id"
      :todo="todo"
      @toggle-complete="$emit('toggle-complete', $event)"
      @delete="$emit('delete', $event)"
    />
  </div>
</template>

<script setup>
import TodoItem from './TodoItem.vue'

defineProps({
  todos: {
    type: Array,
    default: () => []
  }
})

defineEmits(['toggle-complete', 'delete'])
</script>

<style scoped>
.todo-list {
  margin-bottom: 20px;
}

.empty-state {
  background: white;
  padding: 40px 20px;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.empty-state p {
  color: #999;
  font-size: 16px;
}
</style>


