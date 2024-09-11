package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.models.MouvementCaisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MouvementRepository extends JpaRepository<MouvementCaisse, Long> {
boolean existsById(Long id);

    List<MouvementCaisse> findAllByCaisse(Caisse caisse);
}

