package com.ainesh.TeamTaskTracker.dto;

import java.time.LocalDateTime;
import com.ainesh.TeamTaskTracker.enums.ProjectStatTypeEnum;

public record ProjectStatsResponse<T>(
  Long projectId,
  ProjectStatTypeEnum statType,
  T data,
  LocalDateTime timeQueried
) {
}
