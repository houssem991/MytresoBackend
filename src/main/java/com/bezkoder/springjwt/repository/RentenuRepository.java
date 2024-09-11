package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.models.RetenueSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentenuRepository extends JpaRepository<RetenueSource, Long> {
boolean existsById(Long id);
}

