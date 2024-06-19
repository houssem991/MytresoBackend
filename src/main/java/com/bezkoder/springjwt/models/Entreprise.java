package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "entreprise",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "matriculefiscale"),
        })
@Getter
@Setter
public class Entreprise {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;


  @Column(length = 20)
  private String name;
  @Column(length = 20)
  private String matriculefiscale;
  private String adresse;
  private String logo;

  @OneToMany(mappedBy = "entreprise",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
  private Set<User> users;
  public Entreprise() {

  }


}