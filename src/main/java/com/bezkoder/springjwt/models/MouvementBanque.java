package com.bezkoder.springjwt.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "mouvement_banque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MouvementBanque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle")
    private String libelle;
    @Column(name = "piece")
    private String piece;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "mouvement")
    private String mouvement;
    @ManyToOne()
    @JoinColumn(name = "banque_id")
    @JsonIgnore
    private Banque banque;

    // Getters and Setters


}

