package com.bezkoder.springjwt.repository;

import java.util.List;
import java.util.Optional;

import com.bezkoder.springjwt.models.Access;
import com.bezkoder.springjwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRepository extends JpaRepository<Access, Integer> {
    @Query("SELECT a FROM Access a WHERE a not in (Select ar.access from AccessRole ar where ar.role.id = :idrole)")
    List<Access> findAllByRoleId(@Param("idrole") int idrole);
    @Query("SELECT a FROM Access a WHERE a  in (Select ar.access from AccessRole ar where ar.role.id = :idrole)")
    List<Access> findAllAccessByRoleId(@Param("idrole") int idrole);
    Optional<Access> findByTitle(String title);
}

