package com.bezkoder.springjwt.SageModels;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "F_COMPTET")
@Getter
@Setter
public class F_COMPTET {

    @Id
    @Column(name = "CT_Num")
    private String code;

    @Column(name = "CT_Intitule")
    private String nom;

    @Column(name = "CT_Adresse")
    private String adresse;

    @Column(name = "CT_Complement")
    private String complement;

    @Column(name = "CT_CodePostal")
    private String codePostal;

    @Column(name = "CT_Ville")
    private String ville;

    @Column(name = "CT_Pays")
    private String pays;

    @Column(name = "CT_Telephone")
    private String telephone;

    @Column(name = "CT_Telecopie")
    private String telecopie;

    @Column(name = "CT_EMail")
    private String email;

    @Column(name = "CT_Site")
    private String site;

    @Column(name = "CT_Contact")
    private String contact;

    @Column(name = "CT_Type")
    private String type;

    @Column(name = "CT_Siret")
    private String siret;

    @Column(name = "CT_Ape")
    private String ape;

    @Column(name = "CT_Identifiant")
    private String identifiant;


}

