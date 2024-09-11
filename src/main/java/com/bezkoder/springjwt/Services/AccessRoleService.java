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
            acr.setNameaccess(val.getAccess().getTitle());
            if(val.isAjouter()){
                acr.setAdd("✔");
            }else acr.setAdd("✘");
            if(val.isConsulter()){
                acr.setConsulter("✔");
            }else acr.setConsulter("✘");
            if(val.isModifier()){
                acr.setModifier("✔");
            }else acr.setModifier("✘");
            if(val.isSupprimer()){
                acr.setDelete("✔");
            }else acr.setDelete("✘");
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
            acr.setNameaccess(val.getAccess().getTitle());
            if(val.isAjouter()){
                acr.setAdd("✔");
            }else acr.setAdd("✘");
            if(val.isConsulter()){
                acr.setConsulter("✔");
            }else acr.setConsulter("✘");
            if(val.isModifier()){
                acr.setModifier("✔");
            }else acr.setModifier("✘");
            if(val.isSupprimer()){
                acr.setDelete("✔");
            }else acr.setDelete("✘");

            arr.add(acr);
        });
        return arr;
    }

    @Override
    public AccessRole findByRoleandAccess(@Param("idrole") int idrole , @Param("idaccess") int idaccess) {
        return accessRoleRepository.findAccessRoleByAccessIdAndRoleId(idrole,idaccess);
    }
    @Override
    public AccessRole findByRoleandAccessTitle(@Param("idrole") int idrole , @Param("title") String title) {
        return accessRoleRepository.findAccessRoleByAccssTitleAndRoleId(idrole, title);
    }
    @Override
    public ResponseEntity<?> AddAccessRole(AccessroleRequest accessRoleRequest) {
        Role role = roleRepository.findById(accessRoleRequest.getRoleid()).orElseThrow(() -> new EntityNotFoundException("Role not found"));
        Access access = accessRepository.findById(accessRoleRequest.getAccesid()).orElseThrow(() -> new EntityNotFoundException("Access not found"));
        AccessRole accessRole = new AccessRole();
        accessRole.setId(new AccessRolePk(role, access)); // Set the identifier value
        accessRole.setRole(role);
        accessRole.setAccess(access);
        accessRole.setAjouter(accessRoleRequest.isAjouter());
        accessRole.setModifier(accessRoleRequest.isModifier());
        accessRole.setConsulter(accessRoleRequest.isConsulter());
        accessRole.setSupprimer(accessRoleRequest.isSupprimer());
        accessRoleRepository.save(accessRole);
        return ResponseEntity.ok(new MessageResponse("Add Succesful"));
    }

    @Override
    public ResponseEntity<?> UpdateAccessRole(AccessroleRequest accessRoleRequest,@Param("idrole") int idrole , @Param("idaccess") int idaccess ) {
        AccessRole ar = accessRoleRepository.findAccessRoleByAccessIdAndRoleId(idrole,idaccess);
        if(ar.toString().isEmpty()){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: AccessRole not found!"));
        }
        ar.setAjouter(accessRoleRequest.isAjouter());
        ar.setModifier(accessRoleRequest.isModifier());
        ar.setConsulter(accessRoleRequest.isConsulter());
        ar.setSupprimer(accessRoleRequest.isSupprimer());
        accessRoleRepository.save(ar);
        return ResponseEntity.ok(new MessageResponse("Update Succesful"));
    }

    @Override
    public ResponseEntity<?> delete(@Param("idrole") int idrole , @Param("idaccess") int idaccess ) {
        AccessRole ar = accessRoleRepository.findAccessRoleByAccessIdAndRoleId(idrole,idaccess);
        if(ar.toString().isEmpty()){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: AccessRole not found!"));
        }
        accessRoleRepository.delete(ar);
        return ResponseEntity.ok(new MessageResponse("Delete Succesful"));
    }


}
