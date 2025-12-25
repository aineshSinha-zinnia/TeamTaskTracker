package com.ainesh.TeamTaskTracker.dtoMapper;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dto.ProjectCreationDTO;
import com.ainesh.TeamTaskTracker.models.Project;

@Service
public class ProjectCreationMapper implements Function<ProjectCreationDTO, Project> {
  @Override
  public Project apply(ProjectCreationDTO projectCreationDTO) {
    return new Project(null, projectCreationDTO.title(), projectCreationDTO.description(), null, projectCreationDTO.projectedEndDate(), null);
  }
}
