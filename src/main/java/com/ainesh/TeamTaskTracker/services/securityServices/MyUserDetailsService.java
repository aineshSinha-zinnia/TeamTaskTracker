package com.ainesh.TeamTaskTracker.services.securityServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dao.mongoDao.UserRepository;
import com.ainesh.TeamTaskTracker.models.mongoModels.User;
import com.ainesh.TeamTaskTracker.models.security.UserPrincipal;

@Service
public class MyUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  @Autowired
  public MyUserDetailsService(
    UserRepository userRepository
  ) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    
    User user = userRepository
    .findByEmail(email)
    .orElseThrow(
      () -> new UsernameNotFoundException("Given username wasn't found")
    );

    return new UserPrincipal(user);
    
  }
  
}
