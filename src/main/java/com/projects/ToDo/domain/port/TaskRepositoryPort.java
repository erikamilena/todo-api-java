package com.projects.ToDo.domain.port;

import com.projects.ToDo.domain.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepositoryPort {
    Task save(Task task);

    Optional<Task> findById(Long id);

    List<Task> findAll();

    List<Task> getPendingTaskByCategory(String categoryName);

    List<Task> findByDateRange(LocalDateTime start, LocalDateTime end);

    Optional<Task> deleteById(Long id);
}
