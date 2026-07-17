package com.projects.ToDo.infrastructure.persistence.adapter;


import com.projects.ToDo.domain.model.Task;
import com.projects.ToDo.domain.port.TaskRepositoryPort;
import com.projects.ToDo.infrastructure.persistence.entity.TaskEntity;
import com.projects.ToDo.infrastructure.persistence.mapper.TaskPersistenceMapper;
import com.projects.ToDo.infrastructure.persistence.repository.SpringDataTaskRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jboss.logging.NDC.clear;

@Repository
@Primary
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final SpringDataTaskRepository springDataRepository;
    private final TaskPersistenceMapper mapper;

    public TaskRepositoryAdapter(SpringDataTaskRepository springDataRepository, TaskPersistenceMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Task save(Task task) {

        TaskEntity entityToSave = mapper.toEntity(task);

        TaskEntity savedEntity = springDataRepository.save(entityToSave);

        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Task> findById(Long id) {

        Optional<TaskEntity> entityToFind = springDataRepository.findById(id);

        return entityToFind.map(mapper::toDomain);
    }

    @Override
    public List<Task> findAll() {

        List<TaskEntity> entityToFind = springDataRepository.findAll();

        return entityToFind.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Task> getPendingTaskByCategory(String categoryName) {

        List<TaskEntity> entityToFind = springDataRepository.findPendingByCategory(categoryName);

        return entityToFind.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Task> findByDateRange(LocalDateTime start, LocalDateTime end) {

        List<TaskEntity> entityToFind = springDataRepository.findByDateRange(start, end);

        return entityToFind.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Task> deleteById(Long id) {

        Optional<TaskEntity> entityToDelete = springDataRepository.findById(id);

        if (entityToDelete.isPresent()) {
            springDataRepository.deleteById(id);
        }

        return entityToDelete.map(mapper::toDomain);
    }

}
