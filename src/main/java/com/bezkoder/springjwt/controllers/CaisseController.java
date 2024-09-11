package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Services.ICaisseService;
import com.bezkoder.springjwt.Services.IEntrepriseService;
import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.models.Entreprise;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.CaisseRequest;
import com.bezkoder.springjwt.payload.request.EnterpriseRequest;
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
@RequestMapping("/api/caisse")
public class CaisseController {
  @Autowired
  ICaisseService iCaisseService;
  @GetMapping("/all")
  public List<Caisse> all() {
    return iCaisseService.findall();
  }
  @GetMapping("/all/notCurrent/{id}/{iduser}")
  public List<Caisse> allC(@PathVariable("id") Long id , @PathVariable("iduser") Long iduser) {
    return iCaisseService.findallCaisseWithoutCurrentCaisse(id , iduser);
  }


  @GetMapping("/find/{id}")
  public Caisse find(@PathVariable("id") int id)
  {
    return iCaisseService.findbyId(id);
  }
  @PostMapping("/add")
  public ResponseEntity<?> add(@Valid @RequestBody CaisseRequest caisseRequest) {
    return iCaisseService.AddCaisse(caisseRequest);
  }
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete( @PathVariable("id") Long id ) {
    return iCaisseService.deleteCaisse(id);
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<?> Update(@Valid @RequestBody CaisseRequest caisseRequest,@PathVariable("id") int id) {
return iCaisseService.UpdateCaisse(caisseRequest,id);
  }
  @PutMapping("/alimenter/{id}")
  public ResponseEntity<?> Alimenter(@Valid @RequestBody AlimentationRequest alimentationRequest, @PathVariable("id") int id) {
    return iCaisseService.AlimenterCaisse(alimentationRequest,id);
  }
}
