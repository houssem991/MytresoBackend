package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
  Optional<Agent> findByUsername(String username);
  Optional<Agent> findByEmail(String email);
  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  /*@Query("SELECT u FROM User u " +
          "JOIN FETCH u.roles r " +
          "LEFT JOIN FETCH u.clubs c " +
          "WHERE r.name = 'ROLE_RESPONSABLE_CLUB' " +
          "AND (c IS NULL OR u NOT IN (SELECT cu.user FROM Clubs cu))")
  List<User> findallresp();
  @Query("SELECT u FROM User u " +
          "JOIN FETCH u.roles r " +
          "WHERE r.name = 'ROLE_ADMIN' " )
  List<User> admin();*/
}
