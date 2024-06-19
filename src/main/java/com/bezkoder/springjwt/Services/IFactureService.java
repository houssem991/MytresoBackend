package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.SageModels.F_DOCENTETE;
import com.bezkoder.springjwt.models.Facture;
import com.bezkoder.springjwt.models.Fournisseur;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFactureService {

    List<F_DOCENTETE> findall(String idfournisseur);
    List<Facture> findallFactures();
    ResponseEntity<?> ImportFacture(String idfournisseurs);
}
