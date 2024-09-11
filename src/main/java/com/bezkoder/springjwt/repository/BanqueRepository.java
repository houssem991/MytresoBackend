package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Banque;
import com.bezkoder.springjwt.models.Caisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanqueRepository extends JpaRepository<Banque, Long> {
boolean existsById(Long id);
}

