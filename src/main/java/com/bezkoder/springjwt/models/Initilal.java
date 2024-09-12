package com.bezkoder.springjwt.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "initial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Initilal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String element;
    private String initial;
    @ManyToOne()
    @JoinColumn(name = "Owner_id")
    @JsonIgnore
    private User user;

    // Getters and Setters


}

