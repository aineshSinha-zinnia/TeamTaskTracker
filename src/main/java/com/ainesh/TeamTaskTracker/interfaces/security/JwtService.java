package com.ainesh.TeamTaskTracker.interfaces.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  public String generateToken(String email);
  public String extractEmail(String token);
  public Boolean validateToken(String token, UserDetails userDetails);
}
