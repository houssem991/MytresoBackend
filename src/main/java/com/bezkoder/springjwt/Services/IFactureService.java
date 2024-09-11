package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.SageModels.F_DOCENTETE;
import com.bezkoder.springjwt.models.Facture;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.payload.request.RegRequest;
import com.bezkoder.springjwt.payload.response.DetailFactureResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface IFactureService {


    List<F_DOCENTETE> findallFactureFournisseurs(@Param("idfournisseur") String idfournisseur);

    List<F_DOCENTETE> findallFactureClients(@Param("idfournisseur") String idfournisseur);

    List<Facture> findallFacturesByFournisseur(@Param("idfournisseur") String idfournisseur);

    List<Facture> findallFacturesByClient(@Param("idclient") String idclient);

    List<Facture> findallFactureFournisseurs(@Param("iduser") long iduser);
    DetailFactureResponse findFactureClientByPiece(String piece);

    DetailFactureResponse findFactureFournisseursByPiece(String piece);

    List<Facture> findallFactureClients(@Param("iduser") long iduser);
    ResponseEntity<?> ReglerFactureFournisseurs(RegRequest regRequest , String piece);
    ResponseEntity<?> ReglerFactureClient(RegRequest regRequest , String piece);
    ResponseEntity<?> ImportFactureFournisseurs(String idfournisseur);
    List<Facture> findAllNonTotRegleFournisseurs(@Param("code") String code);

    ResponseEntity<?> ImportFactureClient(String idclient);
}
