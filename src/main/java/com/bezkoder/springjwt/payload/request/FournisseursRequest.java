package com.bezkoder.springjwt.payload.request;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FournisseursRequest {

  private String code;

  private String nom;

  private String adresse;

  private String complement;

  private String codePostal;

  private String ville;

  private String pays;

  private String telephone;

  private String telecopie;

  private String email;

  private String site;

  private String contact;

  private String identifiant;




}
