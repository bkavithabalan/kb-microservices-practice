package com.kb.currencyconversion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.kb.currencyconversion.DTO.CurrConversionDto;
import com.kb.currencyconversion.DTO.CurrencyExchangeFeignDto;

@Mapper(componentModel = "spring")
public interface CurrencyExchangeClientMapper {
	
	@Mapping(source="price",target="conversionRate")
	CurrConversionDto toConversionDto(CurrencyExchangeFeignDto feignDto);

}
