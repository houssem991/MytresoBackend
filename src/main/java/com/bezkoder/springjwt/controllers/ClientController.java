package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.Services.IClientService;
import com.bezkoder.springjwt.Services.IFournisseursService;
import com.bezkoder.springjwt.models.Client;
import com.bezkoder.springjwt.models.Fournisseur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/clients")
public class ClientController {
  @Autowired
  IClientService iClientService;



  @GetMapping("/all")
  public List<F_COMPTET> all() {
    return iClientService.findall();
  }
  @GetMapping("/allClient/{identreprise}/{iduser}")
  public List<Client> all(@PathVariable("identreprise") int identreprise, @PathVariable("iduser") int iduser) {
    return iClientService.findallClients(identreprise,iduser);
  }
  @PostMapping("/importer/{iduser}")
  public ResponseEntity<?> Add(@PathVariable("iduser") long iduser) {
    return iClientService.ImportClients(iduser);
  }
}
