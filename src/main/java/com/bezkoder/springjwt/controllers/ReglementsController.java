package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.SageModels.F_CREGLEMENT;
import com.bezkoder.springjwt.SageModels.F_DOCENTETE;
import com.bezkoder.springjwt.SageModels.F_REGLECH;
import com.bezkoder.springjwt.Services.IFactureService;
import com.bezkoder.springjwt.Services.IRegelementService;
import com.bezkoder.springjwt.models.Facture;
import com.bezkoder.springjwt.models.Reglement;
import com.bezkoder.springjwt.payload.request.RegRequest;
import com.bezkoder.springjwt.payload.response.EcheancierResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reglements")
public class ReglementsController {
  @Autowired
  IRegelementService iReglementService;



  @GetMapping("/allF")
  public List<F_CREGLEMENT> all() {
    return iReglementService.findallReglementFournisseurs();
  }
  @GetMapping("/allC/{idclient}")
  public List<F_CREGLEMENT> allC() {
    return iReglementService.findallReglementClients();
  }
  @GetMapping("/alll/{iduser}")
  public List<Reglement> alll(@PathVariable("iduser") Long iduser) {
    return iReglementService.findallReglementFournisseurs(iduser);
  }
  @GetMapping("/date/test/{iduser}")
  public List<EcheancierResponse> test(@PathVariable("iduser") Long iduser) {
    return iReglementService.findAllDateEcheance(iduser);
  }
  @GetMapping("/all/impaye/client/{iduser}")
  public List<Reglement> allImayeClient(@PathVariable("iduser") Long iduser) {
    return iReglementService.findallImpayesClients(iduser);
  }
  @GetMapping("/all/impaye/fournisseur/{iduser}")
  public List<Reglement> allImpayeFournisseur(@PathVariable("iduser") Long iduser) {
    return iReglementService.findallImpayesFournisseurs(iduser);
  }
  @GetMapping("/all/nonAffecte/fournisseur/{iduser}")
  public List<Reglement> allNonAffecteFournisseur(@PathVariable("iduser") Long iduser) {
    return iReglementService.findAllNonAffecteFournisseurs(iduser);
  }
  @GetMapping("/alll/Clients/{iduser}")
  public List<Reglement> alllClient(@PathVariable("iduser") Long iduser) {
    return iReglementService.findallReglementClients(iduser);
  }
  @PostMapping("/importer/fournisseurs/{date1}/{date2}")
  public ResponseEntity<?> ImportFournisseur(@PathVariable("date1")LocalDate datedebut ,@PathVariable("date2") LocalDate datefin) {
    return iReglementService.ImportReglementFournisseurs(datedebut,datefin);
  }
  @PostMapping("/add/fournisseur")
  public ResponseEntity<?> AddBanquaireFournisseur(@Valid @RequestBody RegRequest regRequest) {
    return iReglementService.AddReglementFournisseurs(regRequest);
  }
  @PostMapping("/add/client")
  public ResponseEntity<?>AddBanquaireClient(@Valid @RequestBody RegRequest regRequest) {
  return iReglementService.AddReglementClient(regRequest);
  }
  @PutMapping ("/impaye/fournisseur/{iduser}/s{num}")
  public ResponseEntity<?> retourImpayeFournisseur(@PathVariable("iduser")Long iduser ,@PathVariable("num") Long num) {
    return iReglementService.retourImpayeFournisseurs(iduser,num);
  }
  @PutMapping ("/impaye/client/{iduser}/{num}")
  public ResponseEntity<?> retourImpayeClient(@PathVariable("iduser")Long iduser ,@PathVariable("num") Long num) {
    return iReglementService.retourImpayeClients(iduser,num);
  }
  @PostMapping("/importer/clients/{date1}/{date2}")
  public ResponseEntity<?> Import(@PathVariable("date1")LocalDate datedebut ,@PathVariable("date2") LocalDate datefin) {
    return iReglementService.ImportReglementClient(datedebut,datefin);
  }
  @GetMapping("/find/fournisseur")
  public List<F_REGLECH> findReglementCHFournisseurs() {
    return iReglementService.findReglementCHFournisseurs();
  }

  @GetMapping("/findF/{piece}")
  public List<F_REGLECH> findReglementFournisseursByPiece(@PathVariable("piece") String piece) {
    return iReglementService.findReglementFournisseursByPiece(piece);
  }

  @GetMapping("/findC/{piece}")
  public List<F_REGLECH> findReglementClientByPiece(@PathVariable("piece") String piece) {
    return iReglementService.findReglementClientByPiece(piece);
  }

  @GetMapping("/find/client")
  public List<F_REGLECH> findReglementCHClients() {
    return iReglementService.findReglementCHClients();
  }
  @GetMapping("/find/{id}")
  public Reglement findById(@PathVariable("id") Long id) {
    return iReglementService.findById(id);
  }
}
