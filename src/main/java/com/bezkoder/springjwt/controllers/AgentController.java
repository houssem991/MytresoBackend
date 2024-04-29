package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Services.IAgentService;
import com.bezkoder.springjwt.Services.IUserService;
import com.bezkoder.springjwt.models.Agent;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.AgentRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/agent")
public class AgentController {
  @Autowired
  IAgentService iAgentService;

  @GetMapping("/all")
  public List<Agent> all() {
    return iAgentService.findall();
  }

  @GetMapping("/find/{id}")
  public Agent find(@PathVariable("id") long id)
  {
    return iAgentService.findbyId(id);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") long id ) {
    return iAgentService.delete(id);
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<?> UpdateUser(@Valid @RequestBody AgentRequest agentRequest, @PathVariable("id") long id) {
return iAgentService.UpdateAgent(agentRequest,id);
  }
}
