package com.bezkoder.springjwt.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "imputation_Reglement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImputationReglement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DR_No")
    private Long DR_No;
    @Column(name = "DO_Domaine")
    private int domaine;

    @Column(name = "DO_Type")
    private int type;

    @Column(name = "RC_Montant")
    private double montant;
    @ManyToOne()
    @JoinColumn(name = "RG_No")
    @JsonIgnore
    private Reglement reglement;
    @ManyToOne()
    @JoinColumn(name = "DO_Piece")
    @JsonIgnore
    private Facture facture;
    // Getters and Setters


}

