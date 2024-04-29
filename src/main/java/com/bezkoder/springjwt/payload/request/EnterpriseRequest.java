package com.bezkoder.springjwt.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterpriseRequest {


	@NotBlank
	private String name;


}
