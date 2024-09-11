package com.bezkoder.springjwt.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "facture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facture {

    @Id
    @Column(name = "DO_PIECE")
    private String id;

    @Column(name = "DO_TIER")
    private String clientCode;
    @Column(name = "DO_Domaine")
    private int Domaine;
    @Column(name = "DO_Valide")
    private String Valide;
    @Column(name = "DO_MontantRegle")
    private double MontantRegle;

    @Column(name = "DO_Type")
    private int Type;
    @Column(name = "DO_Souche")
    private int Souche;

    @Column(name = "CT_NumPayeur")
    private String NumPayeur;
    @Column(name = "DO_Expedit")
    private int Expedit;
    @Column(name = "DO_Condition")
    private String Condition;
    @Column(name = "CG_Num")
    private String CG_Num;
    @Column(name = "DO_DATE")
    @Temporal(TemporalType.DATE)
    private Date documentDate;

    @Column(name = "DO_TOTALHT")
    private BigDecimal totalAmountHT;

    @Column(name = "DO_TOTALTTC")
    private BigDecimal totalAmountTTC;
    @Column(name = "DO_NetAPayer")
    private double netAPayer;
    @Column(name = "DO_ResteAPayer")
    private double resteAPayer;
    @Column(name = "DO_Etat")
    @Enumerated(EnumType.STRING)
    private EFacture etat;

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

