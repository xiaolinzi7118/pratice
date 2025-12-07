package com.todolist.service;

import com.todolist.entity.Todo;
import com.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoService {
    
    private final TodoRepository todoRepository;
    
    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
    
    public List<Todo> getAllTodos(String status, String priority, String category, 
                                   LocalDateTime dueDateStart, LocalDateTime dueDateEnd,
                                   String sortBy, String sortOrder) {
        List<Todo> todos = todoRepository.findAll();
        
        // 筛选
        todos = todos.stream()
            .filter(todo -> status == null || status.isEmpty() || 
                           (status.equals("completed") && Boolean.TRUE.equals(todo.getCompleted())) ||
                           (status.equals("pending") && !Boolean.TRUE.equals(todo.getCompleted())))
            .filter(todo -> priority == null || priority.isEmpty() || 
                           (todo.getPriority() != null && todo.getPriority().equals(priority)))
            .filter(todo -> category == null || category.isEmpty() || 
                           (todo.getCategory() != null && todo.getCategory().equals(category)))
            .filter(todo -> dueDateStart == null || 
                           (todo.getDueDate() != null && !todo.getDueDate().isBefore(dueDateStart)))
            .filter(todo -> dueDateEnd == null || 
                           (todo.getDueDate() != null && !todo.getDueDate().isAfter(dueDateEnd)))
            .collect(Collectors.toList());
        
        // 排序
        if (sortBy != null && !sortBy.isEmpty()) {
            String order = (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) ? "desc" : "asc";
            
            todos.sort((t1, t2) -> {
                int result = 0;
                switch (sortBy.toLowerCase()) {
                    case "priority":
                        result = comparePriority(t1.getPriority(), t2.getPriority());
                        break;
                    case "duedate":
                        result = compareDates(t1.getDueDate(), t2.getDueDate());
                        break;
                    case "createdat":
                        result = compareDates(t1.getCreatedAt(), t2.getCreatedAt());
                        break;
                    case "title":
                        result = compareStrings(t1.getTitle(), t2.getTitle());
                        break;
                    default:
                        result = 0;
                }
                return order.equals("desc") ? -result : result;
            });
        }
        
        return todos;
    }
    
    private int comparePriority(String p1, String p2) {
        int v1 = getPriorityValue(p1);
        int v2 = getPriorityValue(p2);
        return Integer.compare(v1, v2);
    }
    
    private int getPriorityValue(String priority) {
        if (priority == null) return 0;
        switch (priority.toUpperCase()) {
            case "HIGH": return 3;
            case "MEDIUM": return 2;
            case "LOW": return 1;
            default: return 0;
        }
    }
    
    private int compareDates(LocalDateTime d1, LocalDateTime d2) {
        if (d1 == null && d2 == null) return 0;
        if (d1 == null) return 1;
        if (d2 == null) return -1;
        return d1.compareTo(d2);
    }
    
    private int compareStrings(String s1, String s2) {
        if (s1 == null && s2 == null) return 0;
        if (s1 == null) return 1;
        if (s2 == null) return -1;
        return s1.compareTo(s2);
    }
    
    public Todo createTodo(Todo todo) {
        if (todo.getCreatedAt() == null) {
            todo.setCreatedAt(java.time.LocalDateTime.now());
        }
        if (todo.getCompleted() == null) {
            todo.setCompleted(false);
        }
        // 设置默认 userId（开发环境，如果没有提供）
        if (todo.getUserId() == null) {
            todo.setUserId(1L); // 默认用户 ID
        }
        return todoRepository.save(todo);
    }
    
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }
    
    public Todo updateTodo(Long id, Todo todoDetails) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
        
        if (todoDetails.getTitle() != null) {
            todo.setTitle(todoDetails.getTitle());
        }
        if (todoDetails.getCompleted() != null) {
            todo.setCompleted(todoDetails.getCompleted());
        }
        if (todoDetails.getPriority() != null) {
            todo.setPriority(todoDetails.getPriority());
        }
        if (todoDetails.getDueDate() != null) {
            todo.setDueDate(todoDetails.getDueDate());
        }
        if (todoDetails.getCategory() != null) {
            todo.setCategory(todoDetails.getCategory());
        }
        if (todoDetails.getDescription() != null) {
            todo.setDescription(todoDetails.getDescription());
        }
        
        return todoRepository.save(todo);
    }
    
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }
    
    public void deleteTodos(List<Long> ids) {
        todoRepository.deleteAllById(ids);
    }
    
    public void updateTodosStatus(List<Long> ids, Boolean completed) {
        List<Todo> todos = todoRepository.findAllById(ids);
        todos.forEach(todo -> todo.setCompleted(completed));
        todoRepository.saveAll(todos);
    }
}

