package com.bezkoder.springjwt.payload.request;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class AgentRequest {
  private String username;
  private String email;
  private String firstname;
  private String lastname;
  private String password;
  private Set<String> access;
  private long userid;


}
