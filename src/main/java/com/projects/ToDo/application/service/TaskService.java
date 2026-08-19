package com.projects.ToDo.application.service;

import com.projects.ToDo.domain.model.Category;
import com.projects.ToDo.domain.model.Task;
import com.projects.ToDo.domain.port.AICategoryServicePort;
import com.projects.ToDo.domain.port.TaskRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {

    private final TaskRepositoryPort taskRepositoryPort;
    private final AICategoryServicePort aiCategoryService;

    public TaskService(TaskRepositoryPort taskRepositoryPort, AICategoryServicePort aiCategoryService) {
        this.taskRepositoryPort = taskRepositoryPort;
        this.aiCategoryService = aiCategoryService;
    }

    public Task createTask(Task taskToCreate) {

        if (taskToCreate.getCategory() == null) {
            Category category =
                    aiCategoryService.categorizeTask(taskToCreate.getTitle());
            taskToCreate = taskToCreate.withCategory(category);
        }
        return taskRepositoryPort.save(taskToCreate);
    }

    public Task findTaskById(Long id) {

        return taskRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with ID: " + id));
    }

    public List<Task> getAllTasks() {

        return taskRepositoryPort.findAll();
    }

    public Task updateTask(Long id, Task task) {

        Task exitingTask = taskRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + id));

        Task updateTask = exitingTask.update(
                task.getTitle(),
                task.getState(),
                task.getCategory());

        return taskRepositoryPort.save(updateTask);
    }

    public List<Task> getPendingTasksByCategory(String categoryName) {

        return taskRepositoryPort.getPendingTaskByCategory(categoryName);
    }

    public List<Task> getTasksByDateRange(LocalDateTime start, LocalDateTime end) {

        return taskRepositoryPort.findByDateRange(start, end);
    }

    public Task deleteTask(Long id) {

        return taskRepositoryPort.deleteById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Can't delete task with id \" + id + \" because it doesn't exist\""));
    }
}
