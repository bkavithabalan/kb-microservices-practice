package com.kb.api_gateway.Controller;

import java.net.URI;
import java.util.Map;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
public class FallbackController {

	@GetMapping("/commanFallBack")
	public Map<String,String> fallback(ServerWebExchange exchange)
	{
		 Route route = exchange
	                .getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		 
		 URI originalUri = exchange
	                .getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);

		return Map.of(
                "message", "Service is currently unavailable. Please try later.",
               // "path", exchange.getRequest().getPath().toString()
                "service",route.getId(),
                "originalRequest",originalUri.toString()
        ); 
	}
}
