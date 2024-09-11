package com.bezkoder.springjwt.SageRepository;


import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.SageModels.F_CREGLEMENT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReglementRepository extends JpaRepository<F_CREGLEMENT, Integer> {
    @Query("SELECT t FROM F_CREGLEMENT t WHERE t.type = 1")
    List<F_CREGLEMENT> findReglementFournisseurs();
    @Query("SELECT t FROM F_CREGLEMENT t WHERE t.type = 0")
    List<F_CREGLEMENT> findReglementClients();

    @Query("SELECT max(t.num) FROM F_CREGLEMENT t")
    Long getNumReglement();
    @Query("SELECT max(t.numPiece) FROM F_CREGLEMENT t")
    String getNumPiece();
}
