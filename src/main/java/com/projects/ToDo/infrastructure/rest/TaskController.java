package com.projects.ToDo.infrastructure.rest;

import com.projects.ToDo.application.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projects.ToDo.infrastructure.api.TaskDTO;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<List<TaskDTO>> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        taskService.createTask(taskDTO);
        //ResponseEntity.status(HttpStatus.CREATED).body(resultado)
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody @Valid TaskDTO taskDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, taskDTO); // taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(updatedTask); //ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/pending-by-category")
    public ResponseEntity<List<TaskDTO>> getPendingTasksByCategory(@RequestParam String categoryName) {
        List<TaskDTO> pendingTasks = taskService.getPendingTasksByCategory(categoryName);
        return ResponseEntity.ok(pendingTasks);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<TaskDTO>> getTasksByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<TaskDTO> tasks = taskService.getTasksByDateRange(start, end);
        return ResponseEntity.ok(tasks);
    }
}
