package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Entreprise;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, String> {
    Boolean existsByCode(String code);
    @Query("SELECT f FROM Fournisseur f WHERE f.user.entreprise.id = :identreprise")
    List<Fournisseur> findallFournisseursByEntrepriseId(@Param("identreprise") int identreprise);
    @Query("SELECT r FROM Fournisseur r WHERE r.user.id = :iduser ")
    List<Fournisseur> findallFournisseursByUserId(@Param("iduser") long iduser);
}

