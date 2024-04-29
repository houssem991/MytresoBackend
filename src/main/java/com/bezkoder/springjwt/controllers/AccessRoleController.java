package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Services.IAccessRoleService;
import com.bezkoder.springjwt.Services.IUserService;
import com.bezkoder.springjwt.models.AccessRole;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.AccessroleRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.AccessRoleResponse;
import com.bezkoder.springjwt.payload.response.UserResponse;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/access")
public class AccessRoleController {
  @Autowired
  IAccessRoleService iAccessRoleService;

  @GetMapping("/all")
  public List<AccessRoleResponse> all() {
    return iAccessRoleService.findall();
  }


  @GetMapping("/find/{idrole}")
  public List<AccessRoleResponse> findU(@PathVariable("idrole") int idrole)
  {
    return iAccessRoleService.findbyRoleId(idrole);
  }
  @PostMapping("/add")
  public ResponseEntity<?> add(@Valid @RequestBody AccessroleRequest accessroleRequest) {
    return iAccessRoleService.AddAccessRole(accessroleRequest);
  }
  @DeleteMapping("/delete/{id}")
  public String delete(@PathVariable("id") long id ) {
    iAccessRoleService.delete(id);
    return "oki";
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<?> Update(@Valid @RequestBody AccessroleRequest accessroleRequest, @PathVariable("id") long id) {
return iAccessRoleService.UpdateAccessRole(accessroleRequest,id);
  }
}
