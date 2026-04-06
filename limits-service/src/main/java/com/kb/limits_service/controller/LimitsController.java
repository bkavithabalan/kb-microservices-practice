package com.kb.limits_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kb.limits_service.Configuration.AppProperties;

@RestController
public class LimitsController {
	
	@Autowired
	AppProperties props;
	
	@GetMapping("/getLimits")
	public String getLimits()
	{
		
		return "min:" + props.getMinimum() + "max:"+ props.getMaximum();
		
	}

}
