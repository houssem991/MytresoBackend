package com.bezkoder.springjwt.payload.response;


import com.bezkoder.springjwt.models.EFacture;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LigneFactureResponse {


	private String id;

	private Integer lineNumber;


	private String reference;

	private String designation;
	private BigDecimal quantity;

	private BigDecimal unitpriceHT;


	private BigDecimal unitPriceTTC;
	private double remise;
	private BigDecimal amountHT;
	private BigDecimal amountTTC;

}