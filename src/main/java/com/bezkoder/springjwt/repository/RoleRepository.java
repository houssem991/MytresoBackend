package com.bezkoder.springjwt.repository;

import java.util.Optional;

import com.bezkoder.springjwt.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  Optional<Role> findByName(String name);
  Boolean existsByName(String name);
}
