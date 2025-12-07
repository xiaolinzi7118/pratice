<template>
  <div class="add-todo">
    <form @submit.prevent="handleSubmit">
      <input
        v-model="title"
        type="text"
        placeholder="输入新任务..."
        class="todo-input"
        required
      />
      <button type="submit" class="add-button">添加</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const emit = defineEmits(['todo-added'])

const title = ref('')

const handleSubmit = () => {
  if (title.value.trim()) {
    emit('todo-added', title.value.trim())
    title.value = ''
  }
}
</script>

<style scoped>
.add-todo {
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.add-todo form {
  display: flex;
  gap: 10px;
}

.todo-input {
  flex: 1;
  padding: 12px 16px;
  border: 2px solid #e0e0e0;
  border-radius: 6px;
  font-size: 16px;
  outline: none;
  transition: border-color 0.3s;
}

.todo-input:focus {
  border-color: #667eea;
}

.add-button {
  padding: 12px 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.add-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.add-button:active {
  transform: translateY(0);
}
</style>


