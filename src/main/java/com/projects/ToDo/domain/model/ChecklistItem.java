package com.projects.ToDo.domain.model;

public class ChecklistItem {

    private final Long id;
    private final String title;
    private final boolean isCompleted;
    private final Long taskId;

    public ChecklistItem(Long id, String title, boolean isCompleted, Long taskId) {
        this.id = id;
        this.title = title;
        this.isCompleted = isCompleted;
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Long getTaskId() {
        return taskId;
    }

    public boolean isValid() {
        return title != null && !title.isEmpty() && taskId != null;
    }

}
