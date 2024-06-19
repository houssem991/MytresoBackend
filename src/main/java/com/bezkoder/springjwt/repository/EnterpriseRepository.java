package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Access;
import com.bezkoder.springjwt.models.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseRepository extends JpaRepository<Entreprise, Integer> {
    Boolean existsByMatriculefiscale(String matriculefiscale);
}

