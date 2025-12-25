package com.ainesh.TeamTaskTracker.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ainesh.TeamTaskTracker.enums.ProjectStatusEnum;
import com.ainesh.TeamTaskTracker.models.Project;

public interface ProjectDao extends JpaRepository<Project, Long> {
  public Page<Project> findByStatus(ProjectStatusEnum projectStatusEnum, Pageable pageable);
}
