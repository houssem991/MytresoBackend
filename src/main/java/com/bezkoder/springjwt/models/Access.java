package com.bezkoder.springjwt.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Access")
@Getter
@Setter
public class Access {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(length = 70)
  private String name;
  @OneToMany(mappedBy = "access",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
@JsonIgnore
  private Set<AccessRole> accessRoles = new HashSet<>();
  public Access() {

  }


}