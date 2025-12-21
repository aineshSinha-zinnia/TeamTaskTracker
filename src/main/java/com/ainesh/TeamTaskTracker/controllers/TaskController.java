package com.ainesh.TeamTaskTracker.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ainesh.TeamTaskTracker.dto.TaskCreationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.TaskResponseDTO;
import com.ainesh.TeamTaskTracker.dto.TaskUpdationRequestDTO;
import com.ainesh.TeamTaskTracker.interfaces.TaskService;

@RestController
@RequestMapping("/work")
public class TaskController {

  @Autowired
  private TaskService taskService;
  
  @GetMapping("/tasks")
  public ResponseEntity<List<TaskResponseDTO>> getAllTasks(){
    List<TaskResponseDTO> tasks = taskService.getAllTasks();
    
    return new ResponseEntity<>(tasks, HttpStatus.OK);
  }

  @PostMapping("/task")
  public ResponseEntity<?> addTask(@RequestBody TaskCreationRequestDTO taskCreationRequestDTO){
    TaskResponseDTO savedTaskDTO = taskService.addTask(taskCreationRequestDTO);

    return new ResponseEntity<>(savedTaskDTO, HttpStatus.OK);
  }

  @GetMapping("/task/{id}")
  public ResponseEntity<?> getTaskById(@PathVariable Long id){
    return taskService.getTaskById(id)
              .map(value -> ResponseEntity.ok(value))
              .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/task/{id}")
  public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskUpdationRequestDTO taskUpdationRequestDTO){
    Optional<TaskResponseDTO> updatedTask = taskService.updateTask(id, taskUpdationRequestDTO);

    return updatedTask
              .map(
                (returnedTask) -> new ResponseEntity<>(returnedTask, HttpStatus.OK)
              )
              .orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
              );
  }

  @DeleteMapping("/task/{id}")
  public ResponseEntity<?> deleteTask(@PathVariable Long id){
    return ResponseEntity.noContent().build();
  }

}
