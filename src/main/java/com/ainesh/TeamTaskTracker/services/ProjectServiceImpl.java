package com.ainesh.TeamTaskTracker.services;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dao.ProjectDao;
import com.ainesh.TeamTaskTracker.dto.ProjectCreationDTO;
import com.ainesh.TeamTaskTracker.dto.ProjectResponseDTO;
import com.ainesh.TeamTaskTracker.dto.ProjectUpdationDTO;
import com.ainesh.TeamTaskTracker.dtoMapper.ProjectCreationMapper;
import com.ainesh.TeamTaskTracker.dtoMapper.ProjectResponseMapper;
import com.ainesh.TeamTaskTracker.dtoMapper.ProjectUpdationMapper;
import com.ainesh.TeamTaskTracker.enums.ProjectStatusEnum;
import com.ainesh.TeamTaskTracker.exceptions.ResourceNotFoundException;
import com.ainesh.TeamTaskTracker.interfaces.ProjectService;
import com.ainesh.TeamTaskTracker.models.Project;
import com.ainesh.TeamTaskTracker.utils.PageableUtil;

@Service
public class ProjectServiceImpl implements ProjectService {
  
  private ProjectCreationMapper projectCreationMapper;
  private ProjectUpdationMapper projectUpdationMapper;
  private ProjectResponseMapper projectResponseMapper;
  private ProjectDao projectDao;

  @Autowired
  public ProjectServiceImpl(ProjectCreationMapper projectCreationMapper, ProjectUpdationMapper projectUpdationMapper, ProjectResponseMapper projectResponseMapper, ProjectDao projectDao){
    this.projectCreationMapper = projectCreationMapper;
    this.projectDao = projectDao;
    this.projectUpdationMapper = projectUpdationMapper;
    this.projectResponseMapper = projectResponseMapper;
  }

  @Override
  public Page<ProjectResponseDTO> getAllProjects(ProjectStatusEnum projectStatusEnum, Pageable pageable) {

    Pageable pageable2 = PageableUtil.enforceSortWhiteList(
      pageable, 
      Set.of("id", "title", "projectedEndDate")
    );

    if(projectStatusEnum != null){
      return projectDao
                .findByStatus(projectStatusEnum, pageable2)
                .map(projectResponseMapper::apply);

    }

    return projectDao
              .findAll(pageable2)
              .map(projectResponseMapper::apply);
  }

  @Override
  public ProjectResponseDTO addProject(ProjectCreationDTO projectCreationDTO) {
    Project project = projectCreationMapper.apply(projectCreationDTO);

    project.setStatus(ProjectStatusEnum.OPEN);

    return projectResponseMapper.apply(projectDao.save(project));
  }

  @Override
  public ProjectResponseDTO updateProject(Long id, ProjectUpdationDTO projectUpdationDTO) {
    
    Project projectToBeUpdated = projectDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));

    Project project = projectUpdationMapper.apply(projectUpdationDTO);

    projectToBeUpdated.setTitle(project.getTitle());
    projectToBeUpdated.setDescription(project.getDescription());
    projectToBeUpdated.setStatus(project.getStatus());
    projectToBeUpdated.setProjectedEndDate(project.getProjectedEndDate());

    return projectResponseMapper.apply(projectDao.save(projectToBeUpdated));

  }

  @Override
  public ProjectResponseDTO getProjectById(Long id) {

    Project project = projectDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find project with id " + id));

    return projectResponseMapper.apply(project);

  }

  @Override
  public void deleteProjectById(Long id) {
    projectDao.deleteById(id);
  }

  @Override
  public void deleteAllProjects() {
    projectDao.deleteAll();
  }

  

}
