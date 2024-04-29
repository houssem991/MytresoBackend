package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.models.objects.AccessRolePk;
import com.bezkoder.springjwt.payload.request.AccessroleRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.AccessRoleResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.payload.response.UserResponse;
import com.bezkoder.springjwt.repository.AccessRepository;
import com.bezkoder.springjwt.repository.AccessRoleRepository;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
class AccessRoleService implements IAccessRoleService {
    @Autowired

    AccessRepository accessRepository;
    @Autowired

    RoleRepository roleRepository;
    @Autowired

    AccessRoleRepository accessRoleRepository;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<AccessRoleResponse> findall() {
        List<AccessRole> ac = accessRoleRepository.findAll();
        List<AccessRoleResponse> arr = new ArrayList<>();
        ac.forEach( val->{
            AccessRoleResponse acr = new AccessRoleResponse();
            acr.setRole_id(val.getRole().getId());
            acr.setName_role(val.getRole().getName());
            acr.setAccess_id(val.getAccess().getId());
            acr.setNameaccess(val.getAccess().getName());
            acr.setAdd(val.isAjouter());
            acr.setModifier(val.isModifier());
            acr.setConsulter(val.isConsulter());
            acr.setDelete(val.isSupprimer());
            arr.add(acr);
        });
        return arr;
    }

    @Override
    public List<AccessRoleResponse> findbyRoleId(@Param("idrole") int idrole) {
        List<AccessRole> ac = accessRoleRepository.findByRoleId(idrole);
        List<AccessRoleResponse> arr = new ArrayList<>();
        ac.forEach( val->{
            AccessRoleResponse acr = new AccessRoleResponse();
            acr.setRole_id(val.getRole().getId());
            acr.setName_role(val.getRole().getName());
            acr.setAccess_id(val.getAccess().getId());
            acr.setNameaccess(val.getAccess().getName());
            acr.setAdd(val.isAjouter());
            acr.setModifier(val.isModifier());
            acr.setConsulter(val.isConsulter());
            acr.setDelete(val.isSupprimer());
            arr.add(acr);
        });
        return arr;
    }

    @Override
    public ResponseEntity<?> AddAccessRole(AccessroleRequest accessRoleRequest) {
        Role role = roleRepository.findById(accessRoleRequest.getRoleid()).orElseThrow(() -> new EntityNotFoundException("Role not found"));
        Access access = accessRepository.findById(accessRoleRequest.getAccesid().longValue()).orElseThrow(() -> new EntityNotFoundException("Access not found"));
        AccessRole accessRole = new AccessRole();
        accessRole.setId(new AccessRolePk(role, access)); // Set the identifier value
        accessRole.setRole(role);
        accessRole.setAccess(access);
        accessRole.setAjouter(accessRoleRequest.isAjouter());
        accessRole.setModifier(accessRoleRequest.isModifier());
        accessRole.setConsulter(accessRoleRequest.isConsulter());
        accessRole.setSupprimer(accessRoleRequest.isSupprimer());
        accessRoleRepository.save(accessRole);
        return ResponseEntity.ok("Add Succesful");
    }

    @Override
    public ResponseEntity<?> UpdateAccessRole(AccessroleRequest AccessRoleRequest, long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return null;
    }
}
