package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Services.IAccessRoleService;
import com.bezkoder.springjwt.Services.IAccessService;
import com.bezkoder.springjwt.Services.IUserService;
import com.bezkoder.springjwt.models.Access;
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
  @Autowired
  IAccessService iAccessService;

  @GetMapping("/all")
  public List<AccessRoleResponse> all() {
    return iAccessRoleService.findall();
  }
  @GetMapping("/allr/{idrole}")
  public List<Access> allr(@PathVariable("idrole") int idrole) {
    return iAccessService.findallAccessByRoleId(idrole);
  }
  @GetMapping("/alla/{idrole}")
  public List<Access> alla(@PathVariable("idrole") int idrole) {
    return iAccessService.findallByRoleId(idrole);
  }

  @GetMapping("/find/{idrole}")
  public List<AccessRoleResponse> findU(@PathVariable("idrole") int idrole)
  {
    return iAccessRoleService.findbyRoleId(idrole);
  }
  @GetMapping("/find/{idrole}/{idaccess}")
  public AccessRole findByRoleAndAccess(@PathVariable("idrole") int idrole ,@PathVariable("idaccess") int idaccess)
  {
    return iAccessRoleService.findByRoleandAccess(idrole,idaccess);
  }
  @GetMapping("/finda/{idrole}/{title}")
  public AccessRole findByRoleAndAccessTitle(@PathVariable("idrole") int idrole ,@PathVariable("title") String title)
  {
    return iAccessRoleService.findByRoleandAccessTitle(idrole,title);
  }
  @PostMapping("/add")
  public ResponseEntity<?> add(@Valid @RequestBody AccessroleRequest accessroleRequest) {
    return iAccessRoleService.AddAccessRole(accessroleRequest);
  }
  @DeleteMapping("/delete/{idrole}/{idaccess}")
  public ResponseEntity<?> delete( @PathVariable("idrole") int idrole ,@PathVariable("idaccess") int idaccess) {
    return iAccessRoleService.delete(idrole,idaccess);
  }
  @PutMapping("/update/{idrole}/{idaccess}")
  public ResponseEntity<?> Update(@Valid @RequestBody AccessroleRequest accessroleRequest, @PathVariable("idrole") int idrole ,@PathVariable("idaccess") int idaccess) {
return iAccessRoleService.UpdateAccessRole(accessroleRequest,idrole ,idaccess);
  }
}
