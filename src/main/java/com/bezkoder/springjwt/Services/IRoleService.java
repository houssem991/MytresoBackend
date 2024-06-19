package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.payload.request.RoleRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRoleService {

    List<Role> findall(int identreprise, int idrole,long iduser);

    Role findbyId(Integer id);
    Role findbyName(String name);
    ResponseEntity<?> AddRole(RoleRequest roleRequest);
     ResponseEntity<?> UpdateRole(RoleRequest roleRequest, int id);
     ResponseEntity<?> delete (Integer id );
}
