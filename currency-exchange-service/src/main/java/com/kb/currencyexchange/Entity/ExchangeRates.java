package com.kb.currencyexchange.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="currency_exchange_rates")
public class ExchangeRates {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String from_currency;
	
	private String to_currency;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal price;

	public String getFrom_currency() {
		return from_currency;
	}

	public void setFrom_currency(String from_currency) {
		this.from_currency = from_currency;
	}

	public String getTo_currency() {
		return to_currency;
	}

	public void setTo_currency(String to_currency) {
		this.to_currency = to_currency;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
