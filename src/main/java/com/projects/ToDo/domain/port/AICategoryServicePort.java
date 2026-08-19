package com.projects.ToDo.domain.port;

import com.projects.ToDo.domain.model.Category;

public interface AICategoryServicePort{
    Category categorizeTask(String title);
}
