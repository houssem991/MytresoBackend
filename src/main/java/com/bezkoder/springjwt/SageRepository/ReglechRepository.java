package com.bezkoder.springjwt.SageRepository;


import com.bezkoder.springjwt.SageModels.F_CREGLEMENT;
import com.bezkoder.springjwt.SageModels.F_REGLECH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReglechRepository extends JpaRepository<F_REGLECH, Integer> {
    @Query("SELECT t FROM F_REGLECH t WHERE t.domaine = 1")
    List<F_REGLECH> findReglementCHFournisseurs();
    @Query("SELECT t FROM F_REGLECH t WHERE t.domaine = 1 and t.piece = :piece")
    List<F_REGLECH> findReglementFournisseursByPiece(@Param("piece") String piece);
    @Query("SELECT t FROM F_REGLECH t WHERE t.domaine = 1 and t.piece = :piece")
    List<F_REGLECH> findReglementClientByPiece(@Param("piece") String piece);
    @Query("SELECT t FROM F_REGLECH t WHERE t.domaine = 0")
    List<F_REGLECH> findReglementCHClients();
    @Query("SELECT distinct t FROM F_REGLECH t WHERE t.num = :num")
    List<F_REGLECH> ReglementNonAffecté(@Param("num") Long num);
    @Query("SELECT COALESCE(SUM(r.monatnt), 0) FROM F_REGLECH r WHERE r.num = :num")
    float findTotalMontantNonAffecté(@Param("num") Long num);
}
