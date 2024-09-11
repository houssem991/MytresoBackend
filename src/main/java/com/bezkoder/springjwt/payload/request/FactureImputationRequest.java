package com.bezkoder.springjwt.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class FactureImputationRequest {

	private double montantregle;
	private String idfacture;



}
