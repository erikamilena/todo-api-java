package com.projects.ToDo.infrastructure.persistence.mapper;

import com.projects.ToDo.domain.model.Category;
import com.projects.ToDo.domain.model.Task;
import com.projects.ToDo.infrastructure.persistence.entity.CategoryEntity;
import com.projects.ToDo.infrastructure.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskPersistenceMapper {

    public TaskEntity toEntity(Task domain) {
        if (domain == null) {
            return null;
        }

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

}
