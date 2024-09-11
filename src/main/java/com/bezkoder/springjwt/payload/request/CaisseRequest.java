package com.bezkoder.springjwt.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaisseRequest {


	@NotBlank
	private String libelle;
	private double solde;
	private long iduser;


}
