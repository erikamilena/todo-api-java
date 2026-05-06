package com.projects.ToDo.domain.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Category {

    private Long id;
    private String name;

    public Category(Long id) {
        this.id = id;
    }

    public boolean isValid() {
            return name != null && !name.isEmpty();
        }

}
