package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Services.IImputaionService;
import com.bezkoder.springjwt.models.ImputationReglement;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.ImputationRequest;
import com.bezkoder.springjwt.payload.response.ImputationResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/imputation")
public class ImputationController {
  @Autowired
  IImputaionService iImputationService;
  @GetMapping("/all/{id}")
  public List<ImputationResponse> all(@PathVariable("id") Long id) {
    return iImputationService.findall(id);
  }


  @GetMapping("/find/{id}")
  public ImputationReglement find(@PathVariable("id") int id)
  {
    return iImputationService.findbyId(id);
  }
  @PostMapping("/add")
  public ResponseEntity<?> add(@Valid @RequestBody ImputationRequest ImputationRequest) {
    return iImputationService.Imputation(ImputationRequest);
  }
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete( @PathVariable("id") Long id ) {
    return iImputationService.deleteImputation(id);
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<?> Update(@Valid @RequestBody ImputationRequest ImputationRequest,@PathVariable("id") int id) {
return iImputationService.UpdateImputation(ImputationRequest,id);
  }
  @PostMapping("/importer/{id}")
  public ResponseEntity<?> Importer( @PathVariable("id") Long id) {
    return iImputationService.ImportImputation(id);
  }
}
