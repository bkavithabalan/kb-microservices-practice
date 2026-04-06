package com.kb.currencyexchange.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kb.currencyexchange.Entity.ExchangeRates;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class CurrencyExchangeRepository {
	
	@PersistenceContext
	EntityManager em;
	
	private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeRepository.class);

	
	public ExchangeRates getExchangeRates(String from, String to)
	{
		TypedQuery<ExchangeRates> query = em.createQuery("select e from ExchangeRates e "
				+ " where from_currency= :from and to_currency= :to", ExchangeRates.class);
		query.setParameter("from", from);
		query.setParameter("to", to);
		ExchangeRates detail = query.getSingleResult();
		logger.info("exchange::"+ detail.getFrom_currency() + "::"+ detail.getTo_currency());
		return detail;
	}

}
