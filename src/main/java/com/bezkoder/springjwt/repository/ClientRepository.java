package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Client;
import com.bezkoder.springjwt.models.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Boolean existsByCode(String code);
    @Query("SELECT c FROM Client c WHERE c.user.entreprise.id = :identreprise")
    List<Client> findallClientsByEntrepriseId(@Param("identreprise") int identreprise);
    @Query("SELECT r FROM Client r WHERE r.user.id = :iduser ")
    List<Client> findallClientsByUserId(@Param("iduser") long iduser);
}

