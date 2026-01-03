package com.ainesh.TeamTaskTracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Autowired
  private JwtFilter jwtFilter;
  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){

    httpSecurity.authorizeHttpRequests(
      request -> request.requestMatchers("/auth/**").permitAll().anyRequest().authenticated()
    )
    .httpBasic(Customizer.withDefaults())
    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
    .csrf(csrf -> csrf.disable());

    return httpSecurity.build();
    
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
    return authenticationConfiguration.getAuthenticationManager();
  }

}
