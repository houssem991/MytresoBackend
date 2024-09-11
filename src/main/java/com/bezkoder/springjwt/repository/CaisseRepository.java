package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.models.Client;
import com.bezkoder.springjwt.models.Reglement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaisseRepository extends JpaRepository<Caisse, Long> {
boolean existsById(Long id);
    @Query("SELECT t FROM Caisse t WHERE t.user.id = :iduser and t.id != :id")
    List<Caisse> findAllCaisseWithoutCurrentCaisse(@Param("iduser") long iduser, @Param("id") long id );
}

