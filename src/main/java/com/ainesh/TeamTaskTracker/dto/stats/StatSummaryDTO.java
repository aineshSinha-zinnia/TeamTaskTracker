package com.ainesh.TeamTaskTracker.dto.stats;

public record StatSummaryDTO(
  Integer openTasks,
  Integer inProgressTasks,
  Integer doneTasks,
  Integer cancelledTasks,
  Integer blockedTasks
) {
}
