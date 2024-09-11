package com.bezkoder.springjwt.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentenueRequest {


	private double pourcentage;
	private long iduser;


}
