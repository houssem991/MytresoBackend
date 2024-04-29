package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.AccessRole;
import com.bezkoder.springjwt.models.Agent;
import com.bezkoder.springjwt.payload.request.AccessroleRequest;
import com.bezkoder.springjwt.payload.request.AgentRequest;
import com.bezkoder.springjwt.payload.response.AccessRoleResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAccessRoleService {

    List<AccessRoleResponse> findall();

    List<AccessRoleResponse> findbyRoleId(int idrole);
    ResponseEntity<?> AddAccessRole(AccessroleRequest AccessRoleRequest);
     ResponseEntity<?> UpdateAccessRole(AccessroleRequest AccessRoleRequest, long id);
     ResponseEntity<?> delete (Long id );
}
