package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.Initilal;
import com.bezkoder.springjwt.models.RetenueSource;
import com.bezkoder.springjwt.payload.request.InitialRequest;
import com.bezkoder.springjwt.payload.request.RentenueRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IInitialService {

    List<Initilal> findall();

    Initilal findbyId(long id);
    ResponseEntity<?> AddInitial(InitialRequest initialRequest);

    ResponseEntity<?> UpdateInitial(InitialRequest initialRequest, long id);

    ResponseEntity<?> deleteInitial(long id);
}
