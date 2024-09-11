package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.models.ImputationReglement;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.CaisseRequest;
import com.bezkoder.springjwt.payload.request.ImputationRequest;
import com.bezkoder.springjwt.payload.response.ImputationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IImputaionService {

    List<ImputationResponse> findall(long id);

    ImputationReglement findbyId(long id);
    ResponseEntity<?> Imputation(ImputationRequest imputationRequest);
    ResponseEntity<?> ImportImputation(long num);

    ResponseEntity<?> UpdateImputation(ImputationRequest imputationRequest, long id);

    ResponseEntity<?> deleteImputation(long id);
}
