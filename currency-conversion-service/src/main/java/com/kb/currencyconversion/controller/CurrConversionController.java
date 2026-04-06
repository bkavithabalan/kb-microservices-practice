package com.kb.currencyconversion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kb.currencyconversion.DTO.CurrConversionDto;
import com.kb.currencyconversion.service.CurrConversionService;

@RestController
@RequestMapping("/curr-conversion")
public class CurrConversionController {

	@Autowired
	CurrConversionService svc;
	
	@GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
	public CurrConversionDto getCurrConvertedValue(@PathVariable String from , @PathVariable String to , @PathVariable double quantity)
	{
		CurrConversionDto dto = svc.getConvertedValue(from, to, quantity);
		return dto;
	}
}
