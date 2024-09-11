package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.models.Entreprise;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.CaisseRequest;
import com.bezkoder.springjwt.payload.request.EnterpriseRequest;
import com.bezkoder.springjwt.payload.response.EntrepriseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICaisseService {

    List<Caisse> findall();
    List<Caisse> findallCaisseWithoutCurrentCaisse(long id , long iuser);

    Caisse findbyId(long id);
    ResponseEntity<?> AddCaisse(CaisseRequest caisseRequest);
    ResponseEntity<?> AlimenterCaisse(AlimentationRequest alimentationRequest, long id );

    ResponseEntity<?> UpdateCaisse(CaisseRequest caisseRequest, long id);

    ResponseEntity<?> deleteCaisse(long id);
}
