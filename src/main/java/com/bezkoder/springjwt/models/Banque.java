package com.bezkoder.springjwt.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "banque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Banque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "telephone")
    private String telephone;
    @Column(name = "solde")
    private double solde;
    @ManyToOne()
    @JoinColumn(name = "Owner_id")
    @JsonIgnore
    private User user;

    // Getters and Setters


}

