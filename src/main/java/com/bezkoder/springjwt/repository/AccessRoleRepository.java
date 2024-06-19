package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.AccessRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRoleRepository extends JpaRepository<AccessRole, Long> {

    @Query("SELECT ar FROM AccessRole ar WHERE ar.role.id = :idrole")
    List<AccessRole> findByRoleId(@Param("idrole") int idrole);

    @Query("SELECT ar FROM AccessRole ar WHERE ar.role.id = :idrole and ar.access.id= :idaccess")
    AccessRole findAccessRoleByAccessIdAndRoleId(@Param("idrole") int idrole, @Param("idaccess") int idaccess);
    @Query("SELECT ar FROM AccessRole ar WHERE ar.role.id = :idrole and ar.access.title= :title")
    AccessRole findAccessRoleByAccssTitleAndRoleId(@Param("idrole") int idrole, @Param("title") String title);
}

