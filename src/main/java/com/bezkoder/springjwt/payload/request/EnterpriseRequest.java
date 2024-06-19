package com.bezkoder.springjwt.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterpriseRequest {


	@NotBlank
	private String name;
	@Column(length = 20)
	private String matriculefiscale;
	private String adresse;
	private long iduser;


}
