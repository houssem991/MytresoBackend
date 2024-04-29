package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.Agent;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.AgentRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAgentService {

    List<Agent> findall();

    Agent findbyId(Long id);
    ResponseEntity<?> AddAgent(AgentRequest agentRequest, long id);
     ResponseEntity<?> UpdateAgent(AgentRequest agentRequest, long id);
     ResponseEntity<?> delete (Long id );
}
