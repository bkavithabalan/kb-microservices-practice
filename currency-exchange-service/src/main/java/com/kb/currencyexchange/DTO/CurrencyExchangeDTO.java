package com.kb.currencyexchange.DTO;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;


public class CurrencyExchangeDTO {
	
	private String from;	
	private String to;	
	private String port;
	private BigDecimal price;
	
		
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	

}
