package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.SageModels.F_DOCENTETE;
import com.bezkoder.springjwt.models.Facture;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.models.Reglement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, String> {
    @Query("SELECT t FROM Facture t WHERE t.fournisseur.code = :idfournisseur")
    List<Facture> findAllFactureByFournisseur(@Param("idfournisseur") String idfournisseur );
    @Query("SELECT t FROM Facture t WHERE t.client.code = :idclient")
    List<Facture> findAllFactureByClient(@Param("idclient") String idclient );
    @Query("SELECT t FROM Facture t WHERE t.fournisseur.user.id = :iduser")
    List<Facture> findAllFactureFournisseurs(@Param("iduser") long iduser );
    @Query("SELECT t FROM Facture t WHERE t.client.user.id = :iduser")
    List<Facture> findAllFactureClients(@Param("iduser") long iduser );
    @Query("SELECT t FROM Facture t WHERE  t.NumPayeur = :code AND (t.etat = com.bezkoder.springjwt.models.EFacture.NonRegle OR t.etat = com.bezkoder.springjwt.models.EFacture.PartRegle) AND t.netAPayer > 0")
    List<Facture> findAllNonTotRegleFournisseurs(@Param("code") String code);
    @Query("SELECT t FROM Facture t WHERE t.Domaine = 1 and t.id= :piece")
    Facture findFactureFournisseur(@Param("piece") String piece );
    @Query("SELECT t FROM Facture t WHERE t.Domaine = 0 and t.id= :piece")
    Facture findFactureClient(@Param("piece") String piece );
}

