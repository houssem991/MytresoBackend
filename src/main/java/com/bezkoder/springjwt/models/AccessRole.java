package com.bezkoder.springjwt.models;

import com.bezkoder.springjwt.models.objects.AccessRolePk;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "access_Role")
@AssociationOverrides({
        @AssociationOverride(name = "id.role", joinColumns = @JoinColumn(name = "Role_Id")),
        @AssociationOverride(name = "id.access", joinColumns = @JoinColumn(name = "Access_Id"))
})
@Getter
@Setter
public class AccessRole {
  @EmbeddedId
  private AccessRolePk id;

  @ManyToOne
  @MapsId("Role_Id")
  @JsonIgnore
  private Role role;

  @ManyToOne
  @MapsId("Access_Id")
  @JsonIgnore
  private Access access;
  boolean ajouter;
  boolean consulter;
  boolean modifier;
  boolean supprimer;

  public AccessRole() {

  }


}