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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper mapper;

    @InjectMocks
    private TaskService taskService;

     @Test
     void shouldReturnAllTaskSuccessfully() {

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
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(expectedDto.getId(), result.get(0).getId());
        assertEquals(expectedDto.getTitle(), result.get(0).getTitle());
        assertEquals(expectedDto.getStatus(), result.get(0).getStatus());
        assertEquals(expectedDto.getCreatedAt(), result.get(0).getCreatedAt());
        assertEquals(expectedDto.getCategoryId(), result.get(0).getCategoryId());

        verify(taskRepository, times(1)).findAll();
        verify(mapper, times(1)).toDomain(dataBaseEntity);
        verify(mapper, times(1)).toDto(domainTask);

     }

     @Test
     void shouldUpdateTaskSuccessfully() {

         // ARRANGE (Set up variables and mock behavior)
            Long id = 1L;
            LocalDateTime taskDate = LocalDateTime.of(2024,6,1,12,0);
            CategoryEntity categoryEntity = new CategoryEntity(1L, "Work");
            Category category = new Category(1L, "Work");

            TaskDTO expectedDto = new TaskDTO(1L, "Learn English", TaskStatus.PENDING, taskDate, 1L);
            TaskEntity existingEntity = new TaskEntity(categoryEntity, 1L, "Learn English", TaskStatus.PENDING, taskDate);

            Task domainTask = new Task(1L, "Learn English", TaskStatus.PENDING,taskDate, category);
            TaskEntity entitySave = new TaskEntity(categoryEntity, 1L, "Learn English", TaskStatus.PENDING, taskDate);
            TaskEntity updatedEntity = new TaskEntity(categoryEntity, 1L, "Learn Spanish", TaskStatus.PENDING, taskDate);

            when(taskRepository.findById(id)).thenReturn(Optional.of(existingEntity));
            when(mapper.toDomainFromDto(expectedDto)).thenReturn(domainTask);
            when(mapper.toEntity(domainTask)).thenReturn(entitySave);
            when(taskRepository.save(any(TaskEntity.class))).thenReturn(updatedEntity);
            when(mapper.toDomain(updatedEntity)).thenReturn(domainTask);
            when(mapper.toDto(domainTask)).thenReturn(expectedDto);

         // ACT
           TaskDTO result = taskService.updateTask(1L, expectedDto);

         // ASSERT
         assertNotNull(result);
         assertEquals(1L, result.getId());
         assertEquals(expectedDto.getTitle(), result.getTitle());
         assertEquals(expectedDto.getStatus(), result.getStatus());
         assertEquals(expectedDto.getCreatedAt(), result.getCreatedAt());
         assertEquals(expectedDto.getCategoryId(), result.getCategoryId());
    }
    @Test
    void shouldFindTaskByIdSuccessfully(){
        // Arrange
        Long id = 1L;
        LocalDateTime taskDate = LocalDateTime.of(2024,6,1,12,0);
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Work");
        Category category = new Category(1L, "Work");

        TaskEntity existingEntity = new TaskEntity(categoryEntity, 1L, "Learn English", TaskStatus.PENDING, taskDate);
        Task domainTask = new Task(1L, "Learn English", TaskStatus.PENDING,taskDate, category);
        TaskDTO expectedDto = new TaskDTO(1L, "Learn English", TaskStatus.PENDING, taskDate, 1L);

        when(taskRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(mapper.toDomain(existingEntity)).thenReturn(domainTask);
        when(mapper.toDto(domainTask)).thenReturn(expectedDto);

        // Act
        TaskDTO result = taskService.findTaskById(id);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(expectedDto.getTitle(), result.getTitle());
        assertEquals(expectedDto.getStatus(), result.getStatus());
        assertEquals(expectedDto.getCreatedAt(), result.getCreatedAt());
        assertEquals(expectedDto.getCategoryId(), result.getCategoryId());
    }


    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
         // Arrange
         Long id = 100L;

         when(taskRepository.findById(id)).thenReturn(Optional.empty());

         // Act
        RuntimeException exception = assertThrows(
                NoSuchElementException.class,
                () -> taskService.findTaskById(id));

        // Assert
        assertEquals("Task not found with ID: 100", exception.getMessage());
    }
}
