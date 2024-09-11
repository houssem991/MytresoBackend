package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Entreprise;
import com.bezkoder.springjwt.models.ImputationReglement;
import com.bezkoder.springjwt.models.Reglement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImputationRepository extends JpaRepository<ImputationReglement, Long> {
    List<ImputationReglement> findAllByReglement(Reglement reglement);
}

