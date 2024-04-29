package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Services.IRoleService;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.payload.request.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/role")
public class RoleController {
  @Autowired
  IRoleService iRoleService;

  @GetMapping("/all")
  public List<Role> all() {
    return iRoleService.findall();
  }

  @GetMapping("/find/{id}")
  public Role find(@PathVariable("id") int id)
  {
    Role u=iRoleService.findbyId(id);



    return u;
  }
  @PostMapping("/add")
  public ResponseEntity<?> Add(@Valid @RequestBody RoleRequest roleRequest) {
    return iRoleService.AddRole(roleRequest);
  }
  @DeleteMapping("/delete/{id}")
  public String delete(@PathVariable("id") int id ) {
    iRoleService.delete(id);
    return "oki";
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<?> UpdateUser(@Valid @RequestBody RoleRequest roleRequest, @PathVariable("id") int id) {
return iRoleService.UpdateRole(roleRequest,id);
  }
}
