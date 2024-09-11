package com.bezkoder.springjwt.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegRequest {

	private LocalDate dateReglement;
	private LocalDate dateEcheance;
	private String reference;
	private String type;
	private String Banque;
	private String numpiece;
	private double solde;
	private double soldedev;
	private long idcaisse;
	private long idbanque;
	private String idclient;
	private String idfournisseur;
	private boolean rs;
	private float percentageRs;




}
