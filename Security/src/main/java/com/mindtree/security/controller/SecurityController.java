package com.mindtree.security.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.security.exception.SecurityException;
import com.mindtree.security.service.SecurityService;
import com.mindtree.security.dto.LoginDTO;
import com.mindtree.security.dto.TokenDTO;

@RestController
public class SecurityController {
	
	final Log LOGGER = LogFactory.getLog(SecurityController.class);
	
	@Autowired
	SecurityService apiGatewayService;
	
	@Autowired
	Environment environment;
	 
	
	
	@PostMapping(value = "/login",produces  = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenDTO> authnetication(@RequestBody LoginDTO login) throws SecurityException{
		LOGGER.info(login);
		TokenDTO token=apiGatewayService.login(login);
		
		String message = environment.getProperty("Service.LOGIN");
		LOGGER.info(message);
		return new ResponseEntity<TokenDTO>(token, HttpStatus.ACCEPTED);
	}
	

}
