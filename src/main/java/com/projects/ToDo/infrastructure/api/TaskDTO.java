package com.projects.ToDo.infrastructure.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projects.ToDo.domain.Model.TaskStatus;
import com.projects.ToDo.infrastructure.persistence.CategoryEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskDTO {

    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "State doesn't can be empty")
    private TaskStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    private Long categoryId;

}
