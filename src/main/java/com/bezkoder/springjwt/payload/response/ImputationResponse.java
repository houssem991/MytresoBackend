package com.bezkoder.springjwt.payload.response;


import com.bezkoder.springjwt.models.Facture;
import com.bezkoder.springjwt.models.Reglement;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImputationResponse {


	private Long DR_No;
	private double montant;
	private long num;
	private String piece;

}