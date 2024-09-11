package com.bezkoder.springjwt.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ImputationRequest {

	private double montant;
	private long idcaisse;
	private long idreglement;
	private String piece;



}
