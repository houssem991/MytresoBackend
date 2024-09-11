package com.bezkoder.springjwt.payload.response;


import com.bezkoder.springjwt.models.EFacture;
import com.bezkoder.springjwt.models.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailFactureResponse {


	private String id;

	private String piece;

	private String clientCode;
	private String nameClient;

	private int Domaine;

	private List<LigneFactureResponse> ligneFactures;

	private Date documentDate;


	private BigDecimal totalAmountHT;

	private BigDecimal totalAmountTTC;

	private double netAPayer;

	private double resteAPayer;

	private EFacture etat;
}