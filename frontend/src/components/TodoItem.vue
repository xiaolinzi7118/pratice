<template>
  <div class="todo-item" :class="{ completed: todo.completed }">
    <div class="todo-content">
      <input
        type="checkbox"
        :checked="todo.completed"
        @change="toggleComplete"
        class="todo-checkbox"
      />
      <span class="todo-title">{{ todo.title }}</span>
    </div>
    <button @click="handleDelete" class="delete-button">删除</button>
  </div>
</template>

<script setup>
const props = defineProps({
  todo: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['toggle-complete', 'delete'])

const toggleComplete = () => {
  emit('toggle-complete', props.todo.id)
}

const handleDelete = () => {
  emit('delete', props.todo.id)
}
</script>

<style scoped>
.todo-item {
  background: white;
  padding: 16px 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
  transition: all 0.3s;
}

.todo-item:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
  transform: translateY(-2px);
}

.todo-item.completed {
  opacity: 0.6;
}

.todo-content {
  display: flex;
  align-items: center;
  gap: 12px;
  flex: 1;
}

.todo-checkbox {
  width: 20px;
  height: 20px;
  cursor: pointer;
  accent-color: #667eea;
}

.todo-title {
  font-size: 16px;
  color: #333;
  flex: 1;
}

.todo-item.completed .todo-title {
  text-decoration: line-through;
  color: #999;
}

.delete-button {
  padding: 8px 16px;
  background: #ff4757;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
  transition: background 0.3s, transform 0.2s;
}

.delete-button:hover {
  background: #ff3838;
  transform: scale(1.05);
}

.delete-button:active {
  transform: scale(1);
}
</style>


