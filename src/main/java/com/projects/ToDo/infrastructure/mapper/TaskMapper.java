package com.projects.ToDo.infrastructure.mapper;

import com.projects.ToDo.domain.Model.Category;
import com.projects.ToDo.domain.Model.Task;
import com.projects.ToDo.infrastructure.api.TaskDTO;
import com.projects.ToDo.infrastructure.persistence.CategoryEntity;
import com.projects.ToDo.infrastructure.persistence.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toDomainFromDto(TaskDTO dto) {
        if (dto == null) return null;

        Category category = null;

        if (dto.getCategoryId() != null) {
            category = new Category(dto.getCategoryId());
        }

        return new Task(
                null,
                dto.getTitle(),
                dto.getStatus(),
                dto.getCreatedAt(),
                category
        );
    }

    public TaskEntity toEntity(Task domain) {
        if (domain == null) return null;

        TaskEntity entity = new TaskEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setState(domain.getState());

        if (domain.getCategory() != null) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(domain.getCategory().getId());
            entity.setCategory(categoryEntity);
        }

        return entity;
    }

    public Task toDomain(TaskEntity entity) {
        if (entity == null) return null;

        Category category = null;

        if (entity.getCategory() != null) {
            category = new Category(entity.getCategory().getId());
        }
        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getState(),
                entity.getCreatedAt(),
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

}
