package com.bezkoder.springjwt.SageRepository;


import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.SageModels.F_DOCENTETE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocRepository extends JpaRepository< F_DOCENTETE, String> {
    @Query("SELECT t FROM F_DOCENTETE t WHERE t.Domaine = 1 and t.NumPayeur= :idfournisseur")
    List<F_DOCENTETE> findFacture(@Param("idfournisseur") String idfournisseur );
}
