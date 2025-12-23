package com.ainesh.TeamTaskTracker.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class EntryController {

  @GetMapping("/")
  public String greeting(){
    return "Hello there";
  }

  @GetMapping("/boom")
  public void boom() throws Exception{
    throw new Exception("Timepass");
  }

}
