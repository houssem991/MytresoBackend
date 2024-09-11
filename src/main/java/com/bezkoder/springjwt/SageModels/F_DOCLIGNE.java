package com.bezkoder.springjwt.SageModels;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "F_DOCLIGNE")
@Getter
@Setter
public class F_DOCLIGNE {

    @Id
    @Column(name = "DL_No")
    private String id;
    @Column(name = "DO_Piece")
    private String piece;

    @Column(name = "CT_Num")
    private String clientCode;
    @Column(name = "DO_Domaine")
    private int Domaine;
    @Column(name = "DL_Ligne")
    private Integer lineNumber;

    @Column(name = "AR_Ref")
    private String reference;

    @Column(name = "DL_Qte")
    private BigDecimal quantity;
    @Column(name = "DL_PrixUnitaire")
    private BigDecimal unitpriceHT;

    @Column(name = "DL_PUTTC")
    private BigDecimal unitPriceTTC;

    @Column(name = "DL_MontantHT")
    private BigDecimal amountHT;
    @Column(name = "DL_MontantTTC")
    private BigDecimal amountTTC;
    @Column(name = "DL_Design")
    private String designation;


}

