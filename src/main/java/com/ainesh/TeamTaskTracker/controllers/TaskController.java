package com.ainesh.TeamTaskTracker.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ainesh.TeamTaskTracker.models.Task;
import com.ainesh.TeamTaskTracker.services.TaskService;

@RestController
@RequestMapping("/work")
public class TaskController {

  private final TaskService taskService;

  @Autowired
  public TaskController(TaskService taskService){
    this.taskService = taskService;
  }
  
  @GetMapping("/tasks")
  public ResponseEntity<?> getAllTasks(){
    Optional<List<Task>> tasks = taskService.getAllTasks();
    
    return tasks
              .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
              .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping("/task")
  public ResponseEntity<?> addTask(@RequestBody Task task){
    Optional<Task> savedTask = taskService.addTask(task);

    return savedTask.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatusCode.valueOf(500)));
  }

  @GetMapping("/task/{id}")
  public ResponseEntity<?> getTaskById(@PathVariable Long id){
    return taskService.getTaskById(id)
              .map(value -> ResponseEntity.ok(value))
              .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/task/{id}")
  public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task){
    Optional<Task> updatedTask = taskService.updateTask(id, task);

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
    return taskService.deleteTask(id) ? new ResponseEntity<>(HttpStatusCode.valueOf(204)) : ResponseEntity.notFound().build();
  }

}
