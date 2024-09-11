package com.bezkoder.springjwt.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rentenue_source")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetenueSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private double pourcentage;
    @ManyToOne()
    @JoinColumn(name = "Owner_id")
    @JsonIgnore
    private User user;

    // Getters and Setters


}

