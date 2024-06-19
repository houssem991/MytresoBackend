package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.Services.IAccessRoleService;
import com.bezkoder.springjwt.Services.IAccessService;
import com.bezkoder.springjwt.Services.IFournisseursService;
import com.bezkoder.springjwt.models.Access;
import com.bezkoder.springjwt.models.AccessRole;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.payload.request.AccessroleRequest;
import com.bezkoder.springjwt.payload.request.RoleRequest;
import com.bezkoder.springjwt.payload.response.AccessRoleResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseursController {
  @Autowired
  IFournisseursService iFournisseursService;



  @GetMapping("/all")
  public List<F_COMPTET> all() {
    return iFournisseursService.findall();
  }
  @GetMapping("/allfournisseur/{identreprise}/{iduser}")
  public List<Fournisseur> all(@PathVariable("identreprise") int identreprise, @PathVariable("iduser") int iduser) {
    return iFournisseursService.findallFournisseurs(identreprise,iduser);
  }
  @PostMapping("/importer/{iduser}")
  public ResponseEntity<?> Add(@PathVariable("iduser") long iduser) {
    return iFournisseursService.ImportFournisseurs(iduser);
  }
}
