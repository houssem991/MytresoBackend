package com.bezkoder.springjwt.repository;

import java.util.Optional;

import com.bezkoder.springjwt.models.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.ERole;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {
    Optional<Access> findByName(ERole name);
}

