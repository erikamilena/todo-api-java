package com.projects.ToDo.infrastructure.rest.controller;

import com.projects.ToDo.application.service.TaskService;
import com.projects.ToDo.domain.model.Task;
import com.projects.ToDo.infrastructure.rest.mapper.TaskRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projects.ToDo.infrastructure.rest.dto.TaskDTO;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskRestMapper taskRestMapper;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {

        Task taskToCreate = taskRestMapper.toNewDomainFromDto(taskDTO);

        Task createdTask = taskService.createTask(taskToCreate);

        TaskDTO responseDto = taskRestMapper.toDto(createdTask);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {

        List<Task> domainTask = taskService.getAllTasks();

        List<TaskDTO> responseDto = taskRestMapper.toDtoList(domainTask);

        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Find a task by its unique ID",
            description = "Returns a single task DTO based on the ID provided in the URL path. Throws a 404 if not found."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found with the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> findTaskById(@PathVariable Long id) {

        Task domainTask = taskService.findTaskById(id);

        TaskDTO responseDto = taskRestMapper.toDto(domainTask);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/pending-by-category")
    public ResponseEntity<List<TaskDTO>> getPendingTasksByCategory(@RequestParam String categoryName) {

        List<Task> domainTasks = taskService.getPendingTasksByCategory(categoryName);

        List<TaskDTO> responseDto = taskRestMapper.toDtoList(domainTasks);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<TaskDTO>> getTasksByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<Task> domainTasks = taskService.getTasksByDateRange(start, end);

        List<TaskDTO> responseDto = taskRestMapper.toDtoList(domainTasks);

        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody @Valid TaskDTO taskDTO) {

        Task taskToUpdate = taskRestMapper.toDomainFromDto(taskDTO);

        Task domainTask = taskService.updateTask(id, taskToUpdate);

        TaskDTO responseDto = taskRestMapper.toDto(domainTask);

        System.out.println("Controller, responseDto: " + responseDto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable Long id) {

        Task domainTask = taskService.deleteTask(id);

        taskRestMapper.toDto(domainTask);

        return ResponseEntity.noContent().build();
    }

}
