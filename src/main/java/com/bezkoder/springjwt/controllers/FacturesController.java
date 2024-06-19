package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.SageModels.F_DOCENTETE;
import com.bezkoder.springjwt.Services.IFactureService;
import com.bezkoder.springjwt.Services.IFournisseursService;
import com.bezkoder.springjwt.models.Facture;
import com.bezkoder.springjwt.models.Fournisseur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/factures")
public class FacturesController {
  @Autowired
  IFactureService iFactureService;



  @GetMapping("/allD/{idfournisseur}")
  public List<F_DOCENTETE> all(@PathVariable("idfournisseur") String idfournisseur) {
    return iFactureService.findall(idfournisseur);
  }
  @GetMapping("/all")
  public List<Facture> allF() {
    return iFactureService.findallFactures();
  }
  @PostMapping("/importer/{id}")
  public ResponseEntity<?> Add(@PathVariable("id") String id) {
    return iFactureService.ImportFacture(id);
  }
}
