package com.ainesh.TeamTaskTracker.dto.security;

public record UserLoginDTO(
  String email,
  String password
) {
}
