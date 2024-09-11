package com.bezkoder.springjwt.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "access")
@Getter
@Setter
public class Access {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(length = 255)
  private String title;
  @Column(length = 255)
  private String path;
  @Column(length = 255)
  private String icon;
  @Column(length = 255)
  private String badge;
  @Column
  private boolean isExternalLink;
  @OneToMany(mappedBy = "access",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
@JsonIgnore
  private Set<AccessRole> accessRoles = new HashSet<>();
  public Access() {

  }


}