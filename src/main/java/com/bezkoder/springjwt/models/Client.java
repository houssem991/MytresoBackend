package com.bezkoder.springjwt.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @Column(name = "Num")
    private String code;

    @Column(name = "Intitule")
    private String nom;

    @Column(name = "Adresse")
    private String adresse;

    @Column(name = "Complement")
    private String complement;

    @Column(name = "CodePostal")
    private String codePostal;

    @Column(name = "Ville")
    private String ville;

    @Column(name = "Pays")
    private String pays;

    @Column(name = "Telephone")
    private String telephone;

    @Column(name = "Telecopie")
    private String telecopie;

    @Column(name = "EMail")
    private String email;

    @Column(name = "Site")
    private String site;

    @Column(name = "Contact")
    private String contact;

    @Column(name = "Type")
    private String type;

    @Column(name = "Siret")
    private String siret;

    @Column(name = "Ape")
    private String ape;

    @Column(name = "Identifiant")
    private String identifiant;


    // Getters and Setters


}

