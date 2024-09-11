package com.bezkoder.springjwt.SageRepository;


import com.bezkoder.springjwt.SageModels.F_DOCENTETE;
import com.bezkoder.springjwt.SageModels.F_DOCLIGNE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneFactureRepository extends JpaRepository<F_DOCLIGNE, String> {
    @Query("SELECT t FROM F_DOCLIGNE t WHERE t.piece= :piece")
    List<F_DOCLIGNE> findLigneFactureFournisseur(@Param("piece") String piece );

}
