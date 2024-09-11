package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.SageModels.F_CREGLEMENT;
import com.bezkoder.springjwt.SageModels.F_DOCENTETE;
import com.bezkoder.springjwt.SageModels.F_REGLECH;
import com.bezkoder.springjwt.models.Facture;
import com.bezkoder.springjwt.models.Reglement;
import com.bezkoder.springjwt.payload.request.RegRequest;
import com.bezkoder.springjwt.payload.response.EcheancierResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface IRegelementService {


    List<F_CREGLEMENT> findallReglementFournisseurs();

    List<F_CREGLEMENT> findallReglementClients();

    List<Reglement> findallImpayesFournisseurs(@Param("iduser") long iduser);
    List<Reglement> findallReglementFournisseurs(@Param("iduser") long iduser);

    List<Reglement> findallImpayesClients(@Param("iduser") long iduser);
    Reglement findById( long id);
    List<Reglement> findallReglementClients(@Param("iduser") long iduser);
    ResponseEntity<?>  retourImpayeClients(@Param("iduser") long iduser,@Param("id") long id);
    ResponseEntity<?>  retourImpayeFournisseurs(@Param("iduser") long iduser,@Param("id") long id);

    ResponseEntity<?> ImportReglementFournisseurs(LocalDate datedebut , LocalDate datefin);
    List<Reglement> findAllNonAffecteFournisseurs(@Param("iduser") long iduser );

    ResponseEntity<?> ImportReglementFournisseurss(LocalDate datedebut, LocalDate datefin);

    ResponseEntity<?> ImportReglementClient(LocalDate datedebut , LocalDate datefin);
    ResponseEntity<?> AddReglementClient(RegRequest regRequest);
    ResponseEntity<?> AddReglementFournisseurs(RegRequest regRequest);
    List<Reglement> findAllByDateEcheance(LocalDate date);
    List<EcheancierResponse> findAllDateEcheance(@Param("iduser") long iduser);
    List<F_REGLECH> findReglementCHFournisseurs();
    List<F_REGLECH> findReglementFournisseursByPiece(@Param("piece") String piece);
    List<F_REGLECH> findReglementClientByPiece(@Param("piece") String piece);
    List<F_REGLECH> findReglementCHClients();
}
