package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.models.MouvementCaisse;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.CaisseRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMouvementService {

    List<MouvementCaisse> findAllByCaisse(long idCaisse);

    ResponseEntity<?> deleteCaisse(long id);
}
