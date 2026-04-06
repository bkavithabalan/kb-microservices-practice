package com.kb.currencyconversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kb.currencyconversion.DTO.CurrConversionDto;
import com.kb.currencyconversion.DTO.CurrencyExchangeFeignDto;
import com.kb.currencyconversion.FeignClients.CurrConversionProxy;
import com.kb.currencyconversion.mapper.CurrencyExchangeClientMapper;

@Service
public class CurrConversionService {
	
	@Autowired
	CurrConversionProxy proxy;
	
	@Autowired
	CurrencyExchangeClientMapper mapper;

	public CurrConversionDto getConvertedValue(String from, String to,double quantity)
	{
		CurrencyExchangeFeignDto response = proxy.getExchangeRates(from, to);
		CurrConversionDto dto = mapper.toConversionDto(response);
		System.out.println("port::"+ response.getPort());
		double calculatedVal = quantity * response.getPrice().doubleValue();
		dto.setCalculatedPrice(calculatedVal);
		dto.setQuantity(quantity);
		return dto;
	}
}
