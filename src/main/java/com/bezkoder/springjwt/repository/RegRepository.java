package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Facture;
import com.bezkoder.springjwt.models.Reglement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegRepository extends JpaRepository<Reglement, Long> {

    @Query("SELECT t FROM Reglement t WHERE t.fournisseur.user.id = :iduser")
    List<Reglement> findAllReglementFournisseurs(@Param("iduser") long iduser );
    @Query("SELECT t FROM Reglement t WHERE t.fournisseur.user.id = :iduser and t.num = :num")
    Reglement findReglementFournisseurs(@Param("iduser") long iduser, @Param("num") long num );
    @Query("SELECT t FROM Reglement t WHERE t.fournisseur.user.id = :iduser and t.impaye = true")
    List<Reglement> findAllImpayesFournisseurs(@Param("iduser") long iduser );
    @Query("SELECT t FROM Reglement t WHERE t.fournisseur.user.id = :iduser AND (t.etat = com.bezkoder.springjwt.models.EReglement.NonAffecter OR t.etat = com.bezkoder.springjwt.models.EReglement.PartAffecter)")
    List<Reglement> findAllNonAffecteFournisseurs(@Param("iduser") long iduser);
    @Query("SELECT t FROM Reglement t WHERE t.client.user.id = :iduser")
    List<Reglement> findAllReglementClients(@Param("iduser") long iduser );
    @Query("SELECT t FROM Reglement t WHERE t.client.user.id = :iduser and t.impaye = true")
    List<Reglement> findAllImpayesClients(@Param("iduser") long iduser );
    @Query("SELECT t FROM Reglement t WHERE t.client.user.id = :iduser and t.num = :num")
    Reglement findReglementClients(@Param("iduser") long iduser, @Param("num") long num );
    @Query("SELECT DISTINCT t.DateEcheance as DateEcheance  FROM Reglement t WHERE t.fournisseur.user.id = :iduser AND t.N_Reglement = :num AND t.DateEcheance >= CURRENT_DATE")
    List<LocalDate> findAllDateEcheance(@Param("iduser") long iduser, @Param("num") long num);

    boolean existsById(Long id);
    @Query("SELECT max(t.num) FROM Reglement t")
    Long getNumReglement();
    @Query("SELECT  t FROM Reglement t WHERE t.DateEcheance = :date and t.N_Reglement = :num")
    List<Reglement> findAllByDateEcheance(@Param("date") LocalDate date, @Param("num") long num);
    @Query("SELECT SUM (t.solde) FROM Reglement t WHERE t.DateEcheance = :date and  t.N_Reglement = :num")
    double SommeByDateEcheance(@Param("date") LocalDate date, @Param("num") long num);
}

