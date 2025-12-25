package com.ainesh.TeamTaskTracker.dto;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatusCode;
import com.ainesh.TeamTaskTracker.enums.ApiErrorCodesEnum;

public record ApiError(
  HttpStatusCode statusCode,
  String message,
  ApiErrorCodesEnum errorCode,
  LocalDateTime timeOfError
) {
}
