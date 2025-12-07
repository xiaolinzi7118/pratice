package com.todolist.controller;

import com.todolist.entity.Todo;
import com.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    
    private final TodoService todoService;
    
    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }
    
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDateEnd,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        
        List<Todo> todos;
        if (status != null || priority != null || category != null || 
            dueDateStart != null || dueDateEnd != null || sortBy != null) {
            todos = todoService.getAllTodos(status, priority, category, 
                                            dueDateStart, dueDateEnd, sortBy, sortOrder);
        } else {
            todos = todoService.getAllTodos();
        }
        return ResponseEntity.ok(todos);
    }
    
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody Todo todo) {
        try {
            Todo createdTodo = todoService.createTodo(todo);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("创建任务失败: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        try {
            Todo updatedTodo = todoService.updateTodo(id, todoDetails);
            return ResponseEntity.ok(updatedTodo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/batch/delete")
    public ResponseEntity<?> deleteTodos(@RequestBody Map<String, List<Long>> request) {
        try {
            List<Long> ids = request.get("ids");
            if (ids == null || ids.isEmpty()) {
                return ResponseEntity.badRequest().body("IDs list is required");
            }
            todoService.deleteTodos(ids);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("批量删除失败: " + e.getMessage());
        }
    }
    
    @PostMapping("/batch/update-status")
    public ResponseEntity<?> updateTodosStatus(@RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<Long> ids = (List<Long>) request.get("ids");
            Boolean completed = (Boolean) request.get("completed");
            
            if (ids == null || ids.isEmpty()) {
                return ResponseEntity.badRequest().body("IDs list is required");
            }
            if (completed == null) {
                return ResponseEntity.badRequest().body("Completed status is required");
            }
            
            todoService.updateTodosStatus(ids, completed);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("批量更新失败: " + e.getMessage());
        }
    }
}

