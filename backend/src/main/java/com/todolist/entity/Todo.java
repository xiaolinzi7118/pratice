package com.todolist.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
public class Todo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private Boolean completed = false;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "priority", nullable = true)
    private String priority; // 优先级：HIGH, MEDIUM, LOW
    
    @Column(name = "due_date", nullable = true)
    private LocalDateTime dueDate; // 截止日期
    
    @Column(name = "category", nullable = true)
    private String category; // 分类/标签
    
    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description; // 任务描述
    
    public Todo() {
        this.completed = false;
    }
    
    public Todo(String title) {
        this.title = title;
        this.completed = false;
    }
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (completed == null) {
            completed = false;
        }
        // 设置默认 userId（如果没有设置）
        if (userId == null) {
            userId = 1L; // 默认用户 ID
        }
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Boolean getCompleted() {
        return completed;
    }
    
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}

