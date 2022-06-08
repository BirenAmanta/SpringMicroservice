package com.mindtree.calldetails.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.calldetails.service.CallDetailsService;
import com.mindtree.calldetails.dto.CallDetailsDTO;
import com.mindtree.calldetails.exception.CallDetailsException;

@RestController
@RequestMapping(value = "/calldetails")
public class CallDetailsController {
	final Log LOGGER = LogFactory.getLog(CallDetailsController.class);

	@Autowired
	private CallDetailsService callDetailsService;

	@Autowired
	Environment environment;

	@GetMapping(value = "/{phoneNo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CallDetailsDTO>> getCustomerCallDetails(@PathVariable long phoneNo)
			throws CallDetailsException {
		List<CallDetailsDTO> detailsList = callDetailsService.getCustomerCallDetails(phoneNo);
		String message = environment.getProperty("");
		LOGGER.info(message);
		return new ResponseEntity<>(detailsList, HttpStatus.FOUND);
	}

}
