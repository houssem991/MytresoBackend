package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Banque;
import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.models.MouvementBanque;
import com.bezkoder.springjwt.models.MouvementCaisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MouvementBanqueRepository extends JpaRepository<MouvementBanque, Long> {
boolean existsById(Long id);

    List<MouvementBanque> findAllByBanque(Banque banque);
}

