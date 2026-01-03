package com.ainesh.TeamTaskTracker.dtoMapper.security;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dto.security.UserRequestDTO;
import com.ainesh.TeamTaskTracker.models.mongoModels.User;

@Service
public class UserRegisterMapper implements Function<UserRequestDTO, User> {

  @Override
  public User apply(UserRequestDTO userRequestDTO) {
    
    return new User(null, userRequestDTO.name(), userRequestDTO.email(), userRequestDTO.password(), null);
    
  }
  
}
