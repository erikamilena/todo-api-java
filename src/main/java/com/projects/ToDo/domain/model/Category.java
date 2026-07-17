package com.projects.ToDo.domain.model;

public class Category {

    private final Long id;
    private final String name;

    public Category(Long id) {
        this.id = id;
        this.name = null;
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isValid() {
        return name != null && !name.isEmpty();
    }

}
