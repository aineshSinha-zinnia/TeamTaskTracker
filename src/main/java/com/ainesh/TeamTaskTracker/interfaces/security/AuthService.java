package com.ainesh.TeamTaskTracker.interfaces.security;

import com.ainesh.TeamTaskTracker.dto.security.UserLoginDTO;
import com.ainesh.TeamTaskTracker.dto.security.UserRequestDTO;

public interface AuthService {
  
  public String registerUser(UserRequestDTO userRequestDTO);
  public String loginUser(UserLoginDTO userLoginDTO);

}
