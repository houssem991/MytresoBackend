package com.bezkoder.springjwt.SageModels;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "F_REGLECH")
@Getter
@Setter
public class F_REGLECH {

    @Id
    @Column(name = "DR_No")
    private Long DR_No;


    @Column(name = "RG_No")
    private Long num;
    @Column(name = "DO_Domaine")
    private int domaine;

    @Column(name = "DO_Type")
    private int type;

    @Column(name = "DO_Piece")
    private String piece;

    @Column(name = "RC_Montant")
    private double monatnt;
    @Column(name = "RG_TypeReg")
    private int typereg;



}

