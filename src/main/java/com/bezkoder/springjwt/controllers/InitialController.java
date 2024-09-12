package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Services.IInitialService;
import com.bezkoder.springjwt.Services.IRetenueService;
import com.bezkoder.springjwt.models.Initilal;
import com.bezkoder.springjwt.models.RetenueSource;
import com.bezkoder.springjwt.payload.request.InitialRequest;
import com.bezkoder.springjwt.payload.request.RentenueRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/initial")
public class InitialController {
  @Autowired
  IInitialService iInitialService;
  @GetMapping("/all")
  public List<Initilal> all() {
    return iInitialService.findall();
  }


  @GetMapping("/find/{id}")
  public Initilal find(@PathVariable("id") int id)
  {
    return iInitialService.findbyId(id);
  }
  @PostMapping("/add")
  public ResponseEntity<?> add(@Valid @RequestBody InitialRequest initialRequest) {
    return iInitialService.AddInitial(initialRequest);
  }
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete( @PathVariable("id") Long id ) {
    return iInitialService.deleteInitial(id);
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<?> Update(@Valid @RequestBody InitialRequest initialRequest,@PathVariable("id") int id) {
return iInitialService.UpdateInitial(initialRequest,id);
  }

}
