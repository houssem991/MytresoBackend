package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Services.IAccessRoleService;
import com.bezkoder.springjwt.Services.IAccessService;
import com.bezkoder.springjwt.Services.IEntrepriseService;
import com.bezkoder.springjwt.models.Access;
import com.bezkoder.springjwt.models.AccessRole;
import com.bezkoder.springjwt.models.Entreprise;
import com.bezkoder.springjwt.payload.request.AccessroleRequest;
import com.bezkoder.springjwt.payload.request.EnterpriseRequest;
import com.bezkoder.springjwt.payload.response.AccessRoleResponse;
import com.bezkoder.springjwt.payload.response.EntrepriseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/entreprise")
public class EntrepriseController {
  @Autowired
  IEntrepriseService iEntrepriseService;
  @GetMapping("/all")
  public List<EntrepriseResponse> all() {
    return iEntrepriseService.findall();
  }


  @GetMapping("/find/{id}")
  public Entreprise find(@PathVariable("id") int id)
  {
    return iEntrepriseService.findbyId(id);
  }
  @PostMapping("/add")
  public ResponseEntity<?> add(@Valid @RequestBody EnterpriseRequest enterpriseRequest) {
    return iEntrepriseService.AddEnreprise(enterpriseRequest);
  }
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete( @PathVariable("id") int id ) {
    return iEntrepriseService.delete(id);
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<?> Update(@Valid @RequestBody EnterpriseRequest enterpriseRequest,@PathVariable("id") int id) {
return iEntrepriseService.UpdateEnreprise(enterpriseRequest,id);
  }
  @PostMapping("/uploadImage/{id}")
  public String uploadImage(@PathVariable("id") int id, @RequestParam("file") MultipartFile file) {
    return iEntrepriseService.uploadImage(id,file);
  }
  @GetMapping("/downloadFile/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
    return iEntrepriseService.downloadFile(fileName,request);
  }
}
