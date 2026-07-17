package com.projects.ToDo;

import com.projects.ToDo.application.service.TaskService;
import com.projects.ToDo.domain.model.Category;
import com.projects.ToDo.domain.model.Task;
import com.projects.ToDo.domain.model.TaskStatus;
import com.projects.ToDo.domain.port.TaskRepositoryPort;
import com.projects.ToDo.infrastructure.persistence.mapper.TaskPersistenceMapper;
import com.projects.ToDo.infrastructure.rest.dto.TaskDTO;
import com.projects.ToDo.infrastructure.rest.mapper.TaskRestMapper;
import com.projects.ToDo.infrastructure.persistence.entity.CategoryEntity;
import com.projects.ToDo.infrastructure.persistence.entity.TaskEntity;
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
    private TaskRepositoryPort taskRepositoryPort;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldReturnAllTask() {
        LocalDateTime taskDate = LocalDateTime.of(2024, 6, 1, 12, 0);
        Category category = new Category(1L, "Work");

        Task domainTask1 = new Task(1L, "Firt test", TaskStatus.PENDING, taskDate, category);
        List<Task> tasks = List.of(domainTask1);

        when(taskRepositoryPort.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(tasks, result);
        verify(taskRepositoryPort).findAll();
    }

    @Test
    void shouldUpdateTask() {
        Long id = 1L;
        LocalDateTime taskDate = LocalDateTime.of(2024, 6, 1, 12, 0);
        Category category = new Category(id, "Work");

        Task existingTask = new Task(id, "Learn English", TaskStatus.PENDING, taskDate, category);
        Task updateTask = new Task(id, "Learn French", TaskStatus.PENDING, taskDate, category);

        when(taskRepositoryPort.findById(id)).thenReturn(Optional.of(existingTask));
        when(taskRepositoryPort.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.updateTask(1L, updateTask);

        assertNotNull(result);
        assertEquals(existingTask.getId(), result.getId());
        assertEquals(updateTask.getTitle(), result.getTitle());
        assertEquals(updateTask.getState(), result.getState());
        assertEquals(updateTask.getCreatedAt(), result.getCreatedAt());
        assertEquals(updateTask.getCategory(), result.getCategory());

        verify(taskRepositoryPort).findById(1L);
        verify(taskRepositoryPort).save(any(Task.class));

    }

    @Test
    void shouldReturnTaskById() {
        Long id = 1L;
        LocalDateTime taskDate = LocalDateTime.of(2024, 6, 1, 12, 0);
        CategoryEntity categoryEntity = new CategoryEntity(id, "Work");
        Category category = new Category(id, "Work");

        Task domainTask = new Task(1L, "Learn English", TaskStatus.PENDING, taskDate, category);

        when(taskRepositoryPort.findById(id)).thenReturn(Optional.of(domainTask));

        Task result = taskService.findTaskById(id);

        assertEquals(domainTask, result);
        verify(taskRepositoryPort).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        Long id = 100L;
        LocalDateTime taskDate = LocalDateTime.of(2024, 6, 1, 12, 0);
        Category category = new Category(id, "Work");

        Task domainTask = new Task(id, "Learn English", TaskStatus.PENDING, taskDate, category);

        when(taskRepositoryPort.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                NoSuchElementException.class,
                () -> taskService.findTaskById(id));

        assertEquals("Task not found with ID: " + id, exception.getMessage());
        verify(taskRepositoryPort, never()).save(domainTask);
    }
}
