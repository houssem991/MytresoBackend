package com.bezkoder.springjwt.SageRepository;


import com.bezkoder.springjwt.SageModels.F_DOCENTETE;
import com.bezkoder.springjwt.SageModels.P_REGLEMENT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface P_ReglementRepository extends JpaRepository<P_REGLEMENT, String> {
P_REGLEMENT findByType(String type);
}
