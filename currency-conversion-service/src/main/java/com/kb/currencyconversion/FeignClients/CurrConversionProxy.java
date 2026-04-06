package com.kb.currencyconversion.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.kb.currencyconversion.DTO.CurrencyExchangeFeignDto;

@FeignClient(name = "currency-exchange-service", path = "/currency-exchange")
public interface CurrConversionProxy {

	@GetMapping("/from/{from}/to/{to}")
	public CurrencyExchangeFeignDto getExchangeRates(@PathVariable String from, @PathVariable String to);

}
