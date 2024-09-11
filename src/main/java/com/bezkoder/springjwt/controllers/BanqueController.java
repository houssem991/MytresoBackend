package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Services.IBanqueService;
import com.bezkoder.springjwt.models.Banque;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.BanqueRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/banque")
public class BanqueController {
  @Autowired
  IBanqueService iBanqueService;
  @GetMapping("/all")
  public List<Banque> all() {
    return iBanqueService.findall();
  }


  @GetMapping("/find/{id}")
  public Banque find(@PathVariable("id") int id)
  {
    return iBanqueService.findbyId(id);
  }
  @PostMapping("/add")
  public ResponseEntity<?> add(@Valid @RequestBody BanqueRequest BanqueRequest) {
    return iBanqueService.AddBanque(BanqueRequest);
  }
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete( @PathVariable("id") Long id ) {
    return iBanqueService.deleteBanque(id);
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<?> Update(@Valid @RequestBody BanqueRequest BanqueRequest,@PathVariable("id") int id) {
return iBanqueService.UpdateBanque(BanqueRequest,id);
  }

}
