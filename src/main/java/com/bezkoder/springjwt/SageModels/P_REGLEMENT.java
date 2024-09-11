package com.bezkoder.springjwt.SageModels;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "P_REGLEMENT")
@Getter
@Setter
public class P_REGLEMENT {

    @Id
    @Column(name = "cbIndice")
    private int id;


    @Column(name = "R_Intitule")
    private String type;



}

