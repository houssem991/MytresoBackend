package com.bezkoder.springjwt.SageModels;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "F_CREGLEMENT")
@Getter
@Setter
public class F_CREGLEMENT {

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




}

