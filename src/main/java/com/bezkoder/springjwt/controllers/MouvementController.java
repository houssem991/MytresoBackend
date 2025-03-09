package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Services.ICaisseService;
import com.bezkoder.springjwt.Services.IMouvementService;
import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.models.MouvementBanque;
import com.bezkoder.springjwt.models.MouvementCaisse;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.CaisseRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mouvement")
public class MouvementController {
  @Autowired
  IMouvementService iMouvementService;
  @GetMapping("/all/{id}")
  public List<MouvementCaisse> all(@PathVariable("id") Long id) {
    return iMouvementService.findAllByCaisse(id);
  }


  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete( @PathVariable("id") Long id ) {
    return iMouvementService.deleteCaisse(id);
  }
  @GetMapping("/all/banque/{id}")
  public List<MouvementBanque> allB(@PathVariable("id") Long id) {
    return iMouvementService.findAllByBanque(id);
  }


  @DeleteMapping("/delete/banque/{id}")
  public ResponseEntity<?> deleteBanque( @PathVariable("id") Long id ) {
    return iMouvementService.deleteMouvementBanque(id);
  }


}
