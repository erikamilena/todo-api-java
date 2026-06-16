package com.projects.ToDo.infrastructure.rest;

import com.projects.ToDo.application.service.TaskService;
import com.projects.ToDo.config.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private JwtUtils jwtUtils;

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
        TaskDTO taskById = taskService.findTaskById(id);
        return ResponseEntity.ok(taskById);
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

    // A simple public endpoint to get a token without needing a username or password
    @GetMapping("/get-token")
    public String getMyLearningToken() {
        // We will generate a token for a placeholder user profile named "learningUser"
        String token = jwtUtils.generateToken("learningUser");

        return "SUCCESS! Copy your token below (do not copy the word Bearer):\n\n" + token;
    }

}
