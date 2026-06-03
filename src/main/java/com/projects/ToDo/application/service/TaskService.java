package com.projects.ToDo.application.service;

import com.projects.ToDo.domain.Model.Task;
import com.projects.ToDo.domain.Repository.TaskRepository;
import com.projects.ToDo.infrastructure.mapper.TaskMapper;
import com.projects.ToDo.infrastructure.persistence.TaskEntity;
import com.projects.ToDo.infrastructure.api.TaskDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {

  private final TaskRepository taskRepository;
  private final TaskMapper mapper;

    public TaskService(TaskRepository taskRepository, TaskMapper mapper) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
    }

    public void createTask(@Valid TaskDTO taskDTO) {
        Task task = mapper.toDomainFromDto(taskDTO);
        TaskEntity entity = mapper.toEntity(task);
        taskRepository.save(entity);
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title is mandatory");
        }
    }

    public TaskDTO findTaskById(Long id) {
        TaskEntity existingEntity = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with ID: " + id));
        Task task = mapper.toDomain(existingEntity);
        return mapper.toDto(task);
    }

    public List<TaskDTO> getAllTasks() {
        List<TaskEntity> entities = taskRepository.findAll();
        return entities.stream()
                .map(mapper::toDomain)
                .map(mapper::toDto)
                .toList();
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new NoSuchElementException("Can't delete task with id " + id + " because it doesn't exist");
        }
        taskRepository.deleteById(id);
    }

    public TaskDTO updateTask(Long id, @Valid TaskDTO taskDTO) {
        TaskEntity existingEntity = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + id));
        Task task = mapper.toDomainFromDto(taskDTO);
        TaskEntity entityToSave = mapper.toEntity(task);
        entityToSave.setId(id);
        TaskEntity updatedEntity =  taskRepository.save(entityToSave);
        return mapper.toDto(mapper.toDomain(updatedEntity));

    }

    public List<TaskDTO> getPendingTasksByCategory(String nameCategory) {
        List<TaskEntity> entities = taskRepository.findPendingByCategory(nameCategory);
        return entities.stream()
               .map(mapper::toDomain)
               .map(mapper::toDto)
               .toList();
    }

    public List<TaskDTO> getTasksByDateRange(LocalDateTime start, LocalDateTime end) {
        List<TaskEntity> entities = taskRepository.findByDateRange(start, end);
        return entities.stream()
                .map(mapper::toDomain)
                .map(mapper::toDto)
                .toList();
    }
}
