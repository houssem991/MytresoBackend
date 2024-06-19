package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Services.IUserService;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired
  IUserService iUserService;

  @GetMapping("/all")
  public List<UserResponse> all() {
    return iUserService.findall();
  }
  @GetMapping("/all/{id}")
  public List<UserResponse> allE(@PathVariable("id") int id) {
    return iUserService.findallUserByEntreprise(id);
  }
  @GetMapping("/find/{id}")
  public UserResponse find(@PathVariable("id") long id)
  {
    UserResponse u =iUserService.findbyIdd(id);



    return u;
  }
  @DeleteMapping("/delete/{id}")
  public String delete(@PathVariable("id") long id ) {
    iUserService.delete(id);
    return "oki";
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<?> UpdateUser(@Valid @RequestBody SignupRequest signUpRequest, @PathVariable("id") long id) {
return iUserService.UpdateUser(signUpRequest,id);
  }
}
