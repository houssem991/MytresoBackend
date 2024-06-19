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
    @Column(name = "DO_PIECE")
    private String documentNumber;

    @Column(name = "DL_LIGNE")
    private Integer lineNumber;

    @Column(name = "AR_REF")
    private String productCode;

    @Column(name = "DL_QTE")
    private BigDecimal quantity;

    @Column(name = "DL_PUTTC")
    private BigDecimal unitPriceTTC;

    @Column(name = "DL_MONTANTTTC")
    private BigDecimal amountTTC;


}

