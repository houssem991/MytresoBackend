package com.bezkoder.springjwt.repository;

import java.util.List;
import java.util.Optional;

import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  Optional<Role> findByName(String name);
  Boolean existsByName(String name);
  @Query("SELECT r FROM Role r WHERE r.user.entreprise.id = :identreprise and r not in (select r from Role r where r.id =:idrole)")
  List<Role> findallRoleByEntrepriseId(@Param("identreprise") int identreprise,@Param("idrole") int idrole);
  @Query("SELECT r FROM Role r WHERE r.user.id = :iduser ")
  List<Role> findallRoleByUserId(@Param("iduser") long iduser);
}

