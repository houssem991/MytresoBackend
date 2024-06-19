package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.models.Access;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.payload.request.EnterpriseRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFournisseursService {

    List<F_COMPTET> findall();
    List<Fournisseur> findallFournisseurs(int identreprise,long iduser);
    ResponseEntity<?> ImportFournisseurs(long iduser);
}
