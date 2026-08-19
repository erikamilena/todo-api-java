package com.projects.ToDo.domain.model;

import com.projects.ToDo.infrastructure.persistence.entity.TaskEntity;

import java.time.LocalDateTime;

public class Task {

    private final Long id;
    private final String title;
    private final TaskStatus state;
    private final LocalDateTime createdAt;
    private final Category category;

    public Task(Long id, String title, TaskStatus state, LocalDateTime createdAt, Category category) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.createdAt = createdAt;
        this.category = category;
    }

    public Task update(String title, TaskStatus status, Category categoryId) {
        return new Task(this.id, title, status, this.createdAt, categoryId);
    }

    public Task withCategory(Category category) {
        return new Task(
                this.id,
                this.title,
                this.state,
                this.createdAt,
                category
        );
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TaskStatus getState() {
        return state;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isValid() {
        return title != null && !title.isEmpty() && state != null && !state.toString().isEmpty() && category != null;
    }

}
