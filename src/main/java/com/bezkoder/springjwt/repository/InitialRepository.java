package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Initilal;
import com.bezkoder.springjwt.models.RetenueSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitialRepository extends JpaRepository<Initilal, Long> {
boolean existsById(Long id);
}

