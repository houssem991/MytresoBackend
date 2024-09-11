package com.bezkoder.springjwt.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BanqueRequest {


	private String nom;

	private String adresse;
	private String telephone;
	private double solde;
	private long iduser;


}
