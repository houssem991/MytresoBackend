package com.bezkoder.springjwt.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "reglement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reglement {

    @Id
    @Column(name = "RG_No")
    private Long num;

    @Column(name = "CT_NumPayeur")
    private String Tiers;

    @Column(name = "RG_Date")
    private LocalDate dateReglement;

    @Column(name = "RG_Reference")
    private String reference;

    @Column(name = "RG_Libelle")
    private String libelle;

    @Column(name = "RG_Montant")
    private double solde;
    @Column(name = "RG_MontantDev")
    private double soldeDev;
    @Column(name = "RG_Impute")
    private Boolean estAffecte;
    @Column(name = "RG_Compta")
    private Boolean estComptablise;
    @Column(name = "RG_Type")
    private int type;
    @Column(name = "RG_TypeReg")
    private int typereglement;
    @Column(name = "RG_Piece")
    private String numPiece;
    @Column(name = "CA_No")
    private long caisse;
    @Column(name = "RG_Banque")
    private int banque;
    @Column(name = "RG_Transfere")
    private int transfere;
    @Column(name = "RG_Cloture")
    private int cloture;
    @Column(name = "RG_Valide")
    private String valide;
    @Column(name = "SoldeRestant")
    private double soldeRestant;
    @Column(name = "N_Reglement")
    private int N_Reglement;
    @Column(name = "RG_DateEchCont")
    private LocalDate DateEcheance;
    @Column(name = "etat")
    private EReglement etat;
    @Column(name = "impaye")
    private boolean impaye;
    @ManyToOne()
    @JoinColumn(name = "fournisseur_num")
    @JsonIgnore
    private Fournisseur fournisseur;
    @ManyToOne()
    @JoinColumn(name = "client_num")
    @JsonIgnore
    private Client client;
    // Getters and Setters


}

