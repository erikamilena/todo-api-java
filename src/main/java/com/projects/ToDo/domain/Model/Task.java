package com.projects.ToDo.domain.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class Task {

    private final Long id;
    private final String title;
    private final TaskStatus state;
    private final LocalDateTime createdAt;
    private Category category;

    public boolean isValid() {
        return title != null && !title.isEmpty() && state != null && !state.toString().isEmpty() && category != null;
    }

}
