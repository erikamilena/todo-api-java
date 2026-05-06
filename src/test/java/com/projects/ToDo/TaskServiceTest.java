package com.projects.ToDo;

import com.projects.ToDo.application.service.TaskService;
import com.projects.ToDo.domain.Model.Category;
import com.projects.ToDo.domain.Model.Task;
import com.projects.ToDo.domain.Model.TaskStatus;
import com.projects.ToDo.domain.Repository.TaskRepository;
import com.projects.ToDo.infrastructure.api.TaskDTO;
import com.projects.ToDo.infrastructure.mapper.TaskMapper;
import com.projects.ToDo.infrastructure.persistence.CategoryEntity;
import com.projects.ToDo.infrastructure.persistence.TaskEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper mapper;

    private TaskService taskService;

     @Test
     void shouldUpdateTaskSuccessfully() {

         //Arrange
        Long taskId = 1L;
        LocalDateTime taskDate = LocalDateTime.of(2024, 6, 1, 12, 0);
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Work");
        Category category = new Category(1L, "Work");

        TaskEntity dataBaseEntity = new TaskEntity(categoryEntity, 19L, "Learn English", TaskStatus.PENDING, taskDate);
        List<TaskEntity> entityList = List.of(dataBaseEntity);
        TaskDTO expectedDto = new TaskDTO(19L, "Learn English", TaskStatus.PENDING, taskDate , 1L);
        Task domainTask = new Task(19L, "Learn English", TaskStatus.PENDING, taskDate , category);

        when(taskRepository.findAll()).thenReturn(entityList); //I stub repository call to return the entityList
         when(mapper.toDomain(dataBaseEntity)).thenReturn(domainTask);
         when(mapper.toDto(domainTask)).thenReturn(expectedDto);


         // Act
        List<TaskDTO> result = taskService.getAllTasks();

         // Assert

     }
}
