package com.ainesh.TeamTaskTracker.dto.stats;

public record StatProgressDTO(
  Integer doneTasks,
  Integer totalTasks,
  Double percentageComplete
) {
  
}
