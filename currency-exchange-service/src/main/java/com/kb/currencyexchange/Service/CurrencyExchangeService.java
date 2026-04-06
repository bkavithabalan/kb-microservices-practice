package com.kb.currencyexchange.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kb.currencyexchange.DTO.CurrencyExchangeDTO;
import com.kb.currencyexchange.Entity.ExchangeRates;
import com.kb.currencyexchange.Mapper.CurrencyExchangeMapper;
import com.kb.currencyexchange.Repository.CurrencyExchangeRepository;

@Service
public class CurrencyExchangeService {

	
	@Autowired
	CurrencyExchangeRepository repo;
	
	@Autowired
	CurrencyExchangeMapper mapper;
	
	public CurrencyExchangeDTO getExchangeRate(String from, String to)
	{
		ExchangeRates rate = repo.getExchangeRates(from, to);
		return mapper.entityToDto(rate);
	}
	
}
