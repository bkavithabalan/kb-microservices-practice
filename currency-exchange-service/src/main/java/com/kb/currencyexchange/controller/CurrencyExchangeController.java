package com.kb.currencyexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kb.currencyexchange.DTO.CurrencyExchangeDTO;
import com.kb.currencyexchange.Service.CurrencyExchangeService;

@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {

	@Autowired
	Environment env;
	
	@Autowired
	CurrencyExchangeDTO dto;
	
	@Autowired
	CurrencyExchangeService svc;
	
	@GetMapping("/from/{from}/to/{to}")
	public CurrencyExchangeDTO getExchangeRates(@PathVariable String from , @PathVariable String to)
	{
		dto = svc.getExchangeRate(from, to);
		dto.setPort(env.getProperty("local.server.port"));
		return dto;
		
		
	}
}
