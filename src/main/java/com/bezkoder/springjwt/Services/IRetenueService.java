package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.models.RetenueSource;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.CaisseRequest;
import com.bezkoder.springjwt.payload.request.RentenueRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRetenueService {

    List<RetenueSource> findall();

    RetenueSource findbyId(long id);
    ResponseEntity<?> AddRentenue(RentenueRequest rentenueRequest);

    ResponseEntity<?> UpdateRetenue(RentenueRequest rentenueRequest, long id);

    ResponseEntity<?> deleteRetenue(long id);
}
