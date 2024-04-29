package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.payload.request.AgentRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.payload.response.UserResponse;
import com.bezkoder.springjwt.repository.AgentRepository;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
class AgentService implements IAgentService {
    @Autowired

    AgentRepository agentRepository;
    @Autowired

    UserRepository userRepository;
    @Autowired

    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<Agent> findall() {
        return agentRepository.findAll();
    }

    @Override
    public Agent findbyId(Long id) {
        return agentRepository.findById(id).get();
    }

    @Override
    public ResponseEntity<?> AddAgent(AgentRequest agentRequest, long id) {
        if (agentRepository.existsByUsername(agentRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (agentRepository.existsByEmail(agentRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Agent agent = new Agent();
        agent.setUsername(agentRequest.getUsername());
        agent.setEmail(agentRequest.getEmail());
        agent.setPassword(encoder.encode(agentRequest.getPassword()));
        /*Set<String> straccess = agentRequest.getAccess();
        Set<AccessRole> accessRoles = new HashSet<>();
        straccess.forEach(val->{
            AccessRole accessRole = accessRepository.findByName(val).get();
            accessRoles.add(accessRole);
        });*/


        //agent.setAccessRoles(accessRoles);
      //  agent.setUser(userRepository.findById(agentRequest.getUserid()).get());
        agentRepository.save(agent);

        return ResponseEntity.ok(new MessageResponse(agent.getId().toString()));
    }

    @Override
    public ResponseEntity<?> UpdateAgent(AgentRequest agentRequest, long id) {

        // Create new user's account
        Agent agent = agentRepository.findById(id).get();
        agent.setUsername(agentRequest.getUsername());
        agent.setEmail(agentRequest.getEmail());
        agent.setPassword(encoder.encode(agentRequest.getPassword()));
        /*Set<String> straccess = agentRequest.getAccess();
        Set<AccessRole> accessRoles = new HashSet<>();
        straccess.forEach(val->{
            AccessRole accessRole = accessRepository.findByName(val).get();
            accessRoles.add(accessRole);
        });*/


        //agent.setAccessRoles(accessRoles);
       // agent.setUser(userRepository.findById(agentRequest.getUserid()).get());
        agentRepository.save(agent);

        return ResponseEntity.ok(new MessageResponse(agent.getId().toString()));    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Agent agent = agentRepository.findById(id).get();
         agentRepository.delete(agent);
        return ResponseEntity.ok(new MessageResponse("Agent deleted successfully"));
    }
}
