package com.ainesh.TeamTaskTracker.dtoMapper;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dto.ProjectUpdationDTO;
import com.ainesh.TeamTaskTracker.models.Project;

@Service
public class ProjectUpdationMapper implements Function<ProjectUpdationDTO, Project> {
  @Override
  public Project apply(ProjectUpdationDTO projectUpdationDTO) {
    return new Project(null, projectUpdationDTO.title(), projectUpdationDTO.description(), projectUpdationDTO.status(), projectUpdationDTO.projectedEndDate(), null);
  }
}
