package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Services.IBanqueService;
import com.bezkoder.springjwt.Services.IRetenueService;
import com.bezkoder.springjwt.models.Banque;
import com.bezkoder.springjwt.models.RetenueSource;
import com.bezkoder.springjwt.payload.request.BanqueRequest;
import com.bezkoder.springjwt.payload.request.RentenueRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/retenue")
public class RetenueController {
  @Autowired
  IRetenueService iRetenueService;
  @GetMapping("/all")
  public List<RetenueSource> all() {
    return iRetenueService.findall();
  }


  @GetMapping("/find/{id}")
  public RetenueSource find(@PathVariable("id") int id)
  {
    return iRetenueService.findbyId(id);
  }
  @PostMapping("/add")
  public ResponseEntity<?> add(@Valid @RequestBody RentenueRequest rentenueRequest) {
    return iRetenueService.AddRentenue(rentenueRequest);
  }
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete( @PathVariable("id") Long id ) {
    return iRetenueService.deleteRetenue(id);
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<?> Update(@Valid @RequestBody RentenueRequest rentenueRequest,@PathVariable("id") int id) {
return iRetenueService.UpdateRetenue(rentenueRequest,id);
  }

}
