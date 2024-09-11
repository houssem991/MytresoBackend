package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.Banque;
import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.BanqueRequest;
import com.bezkoder.springjwt.payload.request.CaisseRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBanqueService {

    List<Banque> findall();

    Banque findbyId(long id);
    ResponseEntity<?> AddBanque(BanqueRequest banqueRequest);

    ResponseEntity<?> UpdateBanque(BanqueRequest banqueRequest, long id);

    ResponseEntity<?> deleteBanque(long id);
}
