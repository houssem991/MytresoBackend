package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.SageModels.F_DOCENTETE;
import com.bezkoder.springjwt.Services.IFactureService;
import com.bezkoder.springjwt.Services.IFournisseursService;
import com.bezkoder.springjwt.models.Facture;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.payload.request.RegRequest;
import com.bezkoder.springjwt.payload.response.DetailFactureResponse;
import jakarta.validation.Valid;
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
    return iFactureService.findallFactureFournisseurs(idfournisseur);
  }
  @GetMapping("/allNonAffecte/{code}")
  public List<Facture> NonAfffecte(@PathVariable("code") String code) {
    return iFactureService.findAllNonTotRegleFournisseurs(code);
  }
  @GetMapping("/allC/{idclient}")
  public List<F_DOCENTETE> allC(@PathVariable("idclient") String idclient) {
    return iFactureService.findallFactureClients(idclient);
  }
  @GetMapping("/allF/{idfournisseur}")
  public List<Facture> allF(@PathVariable("idfournisseur") String idfournisseur) {
    return iFactureService.findallFacturesByFournisseur(idfournisseur);
  }
  @GetMapping("/findallC/{idclient}")
  public List<Facture> findallFacturesByClient(@PathVariable("idclient") String idclient) {
    return iFactureService.findallFacturesByClient(idclient);
  }
  @GetMapping("/alll/{iduser}")
  public List<Facture> alll(@PathVariable("iduser") Long iduser) {
    return iFactureService.findallFactureFournisseurs(iduser);
  }
  @GetMapping("/alll/Clients/{iduser}")
  public List<Facture> alllClient(@PathVariable("iduser") Long iduser) {
    return iFactureService.findallFactureClients(iduser);
  }
  @GetMapping("/find/Client/{piece}")
  public DetailFactureResponse findByPieceClient(@PathVariable("piece") String piece) {
    return iFactureService.findFactureClientByPiece(piece);
  }
  @GetMapping("/find/fournisseur/{piece}")
  public DetailFactureResponse findByPieceFournisseur(@PathVariable("piece") String piece) {
    return iFactureService.findFactureFournisseursByPiece(piece);
  }
  @PostMapping("/importer/fournisseurs/{id}")
  public ResponseEntity<?> Add(@PathVariable("id") String id) {
    return iFactureService.ImportFactureFournisseurs(id);
  }
  @PostMapping("/regler/fournisseurs/{id}")
  public ResponseEntity<?> ReglerDocumentFournisseurs(@PathVariable("id") String id ,@Valid @RequestBody RegRequest regRequest) {
    return iFactureService.ReglerFactureFournisseurs(regRequest,id);
  }
  @PostMapping("/regler/clients/{id}")
  public ResponseEntity<?> ReglerDocumentClients(@PathVariable("id") String id ,@Valid @RequestBody RegRequest regRequest) {
    return iFactureService.ReglerFactureClient(regRequest,id);
  }
  @PostMapping("/importer/clients/{id}")
  public ResponseEntity<?> Import(@PathVariable("id") String id) {
    return iFactureService.ImportFactureClient(id);
  }
}
