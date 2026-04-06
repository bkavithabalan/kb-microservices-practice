package com.kb.currencyexchange.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.kb.currencyexchange.DTO.CurrencyExchangeDTO;
import com.kb.currencyexchange.Entity.ExchangeRates;

@Mapper(componentModel = "spring")
public interface CurrencyExchangeMapper {

	@Mapping(source="from_currency",target="from")
	@Mapping(source="to_currency",target="to")
	CurrencyExchangeDTO entityToDto(ExchangeRates entity);
	
	@Mapping(source="from",target="from_currency")
	@Mapping(source="to",target="to_currency")
	ExchangeRates dtoToEntity(CurrencyExchangeDTO dto);
	
}
