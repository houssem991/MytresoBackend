package com.bezkoder.springjwt.SageModels;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "F_DOCENTETE")
@Getter
@Setter
public class F_DOCENTETE {

    @Id
    @Column(name = "DO_Piece")
    private String id;

    @Column(name = "DO_Tiers")
    private String clientCode;
    @Column(name = "DO_Domaine")
    private int Domaine;
    @Column(name = "DO_Valide")
    private int Valide;
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
    private double NetAPayer;


}

