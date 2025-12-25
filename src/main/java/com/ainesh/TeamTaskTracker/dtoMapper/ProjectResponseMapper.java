package com.ainesh.TeamTaskTracker.dtoMapper;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dto.ProjectResponseDTO;
import com.ainesh.TeamTaskTracker.models.Project;

@Service
public class ProjectResponseMapper implements Function<Project, ProjectResponseDTO> {
  @Override
  public ProjectResponseDTO apply(Project project) {
    return new ProjectResponseDTO(project.getId(), project.getTitle(), project.getDescription(), project.getStatus(), project.getProjectedEndDate());
  }
}
