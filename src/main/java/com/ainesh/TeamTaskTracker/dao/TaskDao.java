package com.ainesh.TeamTaskTracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ainesh.TeamTaskTracker.models.Task;

@Repository
public interface TaskDao extends JpaRepository<Task, Long> {
  
}
