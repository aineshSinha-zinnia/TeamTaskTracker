package com.ainesh.TeamTaskTracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ainesh.TeamTaskTracker.dto.TaskCreationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.TaskResponseDTO;
import com.ainesh.TeamTaskTracker.dto.TaskUpdationRequestDTO;
import com.ainesh.TeamTaskTracker.enums.TaskStatusEnum;
import com.ainesh.TeamTaskTracker.interfaces.TaskService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/work")
public class TaskController {

  @Autowired
  private TaskService taskService;
  
  @GetMapping("/tasks")
  public ResponseEntity<?> getAllTasks(
    @RequestParam(required = false) TaskStatusEnum status,
    Pageable pageable
  ){
    var tasks = taskService.getAllTasks(status, pageable);
    
    return new ResponseEntity<>(tasks, HttpStatus.OK);
  }

  @PostMapping("/task")
  public ResponseEntity<?> addTask(@Valid @RequestBody TaskCreationRequestDTO taskCreationRequestDTO){
    // method argument invalid possible - bad request 400 as well as 500
    TaskResponseDTO savedTaskDTO = taskService.addTask(taskCreationRequestDTO);

    return new ResponseEntity<>(savedTaskDTO, HttpStatus.OK);
  }

  @GetMapping("/task/{id}")
  public ResponseEntity<?> getTaskById(@PathVariable Long id){
    // should give an error, 404 maybe
    return ResponseEntity.ok(taskService.getTaskById(id));
  }

  @PutMapping("/task/{id}")
  public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdationRequestDTO taskUpdationRequestDTO){
    // either 404 as well as 500 or even method invalid - 400/404/500
    TaskResponseDTO updatedTask = taskService.updateTask(id, taskUpdationRequestDTO);

    return new ResponseEntity<>(updatedTask, HttpStatus.OK);
  }

  @DeleteMapping("/task/{id}")
  public ResponseEntity<?> deleteTask(@PathVariable Long id){
    // on error, 500 code
    return ResponseEntity.noContent().build();
  }

}
