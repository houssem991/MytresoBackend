package com.bezkoder.springjwt.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bezkoder.springjwt.payload.response.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  @Query("SELECT u FROM User u WHERE u.entreprise.id = :identreprise")
  List<User> findUsersByEntrepriseId(@Param("identreprise") int identreprise);

 /* @Query("SELECT u FROM User u " +
          "JOIN FETCH u.roles r " +
          "WHERE r.name = 'ROLE_ADMIN' " )
  List<User> admin();*/
}
