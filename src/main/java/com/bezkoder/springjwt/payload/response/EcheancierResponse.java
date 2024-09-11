package com.bezkoder.springjwt.payload.response;


import com.bezkoder.springjwt.models.Reglement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EcheancierResponse {


	private LocalDate Date;

	private double somme;


	private List<Reglement> reglements;



}