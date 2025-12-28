package com.ainesh.TeamTaskTracker.dto.stats;

public record StatActivityDTO(
  Integer createdCount,
  Integer updatedCount,
  Integer completedCount
) {
  
}
