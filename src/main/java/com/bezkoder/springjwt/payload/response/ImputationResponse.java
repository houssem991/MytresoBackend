package com.bezkoder.springjwt.payload.response;


import com.bezkoder.springjwt.models.EFacture;
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


	private double montant;
	private String code;
	private String piece;
	private EFacture etat;

}