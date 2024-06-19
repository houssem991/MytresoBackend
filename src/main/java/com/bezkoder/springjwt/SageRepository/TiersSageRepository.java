package com.bezkoder.springjwt.SageRepository;


import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.models.AccessRole;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TiersSageRepository extends JpaRepository<F_COMPTET, Integer> {
    @Query("SELECT t FROM F_COMPTET t WHERE t.type = '1'")
    List<F_COMPTET> findFournisseurs();
}
