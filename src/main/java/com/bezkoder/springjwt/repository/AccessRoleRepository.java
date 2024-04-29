package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Access;
import com.bezkoder.springjwt.models.AccessRole;
import com.bezkoder.springjwt.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessRoleRepository extends JpaRepository<AccessRole, Long> {

    @Query("SELECT ar FROM AccessRole ar WHERE ar.role.id = :idrole")
    List<AccessRole> findByRoleId(@Param("idrole") int idrole);
}

