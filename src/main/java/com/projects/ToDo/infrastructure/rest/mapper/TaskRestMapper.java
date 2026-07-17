package com.projects.ToDo.infrastructure.rest.mapper;

import com.projects.ToDo.domain.model.Category;
import com.projects.ToDo.domain.model.Task;
import com.projects.ToDo.domain.model.TaskStatus;
import com.projects.ToDo.infrastructure.rest.dto.TaskDTO;
import com.projects.ToDo.infrastructure.persistence.entity.CategoryEntity;
import com.projects.ToDo.infrastructure.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskRestMapper {

    public Task toDomainFromDto(TaskDTO dto) {
        if (dto == null) return null;

        Category category = null;
        if (dto.getCategoryId() != null) {
            category = new Category(dto.getCategoryId());
        }

        return new Task(
                dto.getId(),
                dto.getTitle(),
                dto.getStatus(),
                dto.getCreatedAt(),
                category
        );
    }

    public Task toNewDomainFromDto(TaskDTO dto) {
        Category category = (dto.getCategoryId() != null) ? new Category(dto.getCategoryId()) : null;
        return new Task(
                null,
                dto.getTitle(),
                TaskStatus.PENDING, // Force PENDING for new tasks
                LocalDateTime.now(), // Force NOW for new tasks
                category
        );
    }

    public TaskDTO toDto(Task domain) {
        if (domain == null) return null;

        TaskDTO dto = new TaskDTO();
        dto.setId(domain.getId());
        dto.setTitle(domain.getTitle());
        dto.setStatus(domain.getState());

        if (domain.getCategory() != null) {
            dto.setCategoryId(domain.getCategory().getId());
        }
        return dto;
    }

    public List<TaskDTO> toDtoList(List<Task> domainTasks) {
        if (domainTasks == null) {
            return Collections.emptyList();
        }

        return domainTasks.stream()
                .map(this::toDto) //the same: .map(task -> toDto(task))
                .collect(Collectors.toList());
    }

}
