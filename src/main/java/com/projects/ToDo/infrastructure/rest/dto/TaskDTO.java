package com.projects.ToDo.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projects.ToDo.domain.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long id;

    @NotBlank(message = "Title is mandatory and cannot be empty")
    private String title;

    @NotNull(message = "State doesn't can be empty")
    private TaskStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    private Long categoryId;

}
