package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 20)
  private String name;
  @OneToMany(mappedBy = "role",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<AccessRole> accessRoles = new HashSet<>();
  public Role() {

  }

}