package com.ainesh.TeamTaskTracker.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ainesh.TeamTaskTracker.dto.ProjectCreationDTO;
import com.ainesh.TeamTaskTracker.dto.ProjectResponseDTO;
import com.ainesh.TeamTaskTracker.dto.ProjectUpdationDTO;
import com.ainesh.TeamTaskTracker.enums.ProjectStatusEnum;

public interface ProjectService {
  Page<ProjectResponseDTO> getAllProjects(ProjectStatusEnum status, Pageable pageable);
  ProjectResponseDTO addProject(ProjectCreationDTO projectCreationDTO);
  ProjectResponseDTO updateProject(Long id, ProjectUpdationDTO projectUpdationDTO);
  ProjectResponseDTO getProjectById(Long id);
  void deleteProjectById(Long id);  
  void deleteAllProjects();
}
