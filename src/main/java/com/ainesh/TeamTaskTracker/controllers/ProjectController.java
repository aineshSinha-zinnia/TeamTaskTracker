package com.ainesh.TeamTaskTracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ainesh.TeamTaskTracker.dto.ProjectCreationDTO;
import com.ainesh.TeamTaskTracker.dto.ProjectResponseDTO;
import com.ainesh.TeamTaskTracker.dto.ProjectUpdationDTO;
import com.ainesh.TeamTaskTracker.enums.ProjectStatusEnum;
import com.ainesh.TeamTaskTracker.services.ProjectServiceImpl;
import jakarta.validation.Valid;

@RestController
public class ProjectController {

  @Autowired
  private ProjectServiceImpl projectServiceImpl;

  @GetMapping("/projects")
  public ResponseEntity<?> getAllProjects(
    @RequestParam(required = false) ProjectStatusEnum status,
    Pageable pageable
  ){
    Page<ProjectResponseDTO> projects = projectServiceImpl.getAllProjects(status, pageable);
    return new ResponseEntity<>(projects, HttpStatus.OK);
  }

  @PostMapping("/project")
  public ResponseEntity<?> addProject(@Valid @RequestBody ProjectCreationDTO projectCreationDTO){

    ProjectResponseDTO projectResponseDTO = projectServiceImpl.addProject(projectCreationDTO);

    return ResponseEntity.ok(projectResponseDTO);
  }

  @PutMapping("/project/{id}")
  public ResponseEntity<?> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectUpdationDTO projectUpdationDTO){

    ProjectResponseDTO projectResponseDTO = projectServiceImpl.updateProject(id, projectUpdationDTO);

    return new ResponseEntity<>(projectResponseDTO, HttpStatus.OK);

  }

  @GetMapping("/project/{id}")
  public ResponseEntity<?> getProjectById(@PathVariable Long id){

    ProjectResponseDTO projectResponseDTO = projectServiceImpl.getProjectById(id);

    return new ResponseEntity<>(projectResponseDTO, HttpStatus.OK);

  }

  @DeleteMapping("/project/{id}")
  public ResponseEntity<?> deleteProjectById(@PathVariable Long id){
    projectServiceImpl.deleteProjectById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/projects")
  public ResponseEntity<?> deleteAllProjects(){
    projectServiceImpl.deleteAllProjects();
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
