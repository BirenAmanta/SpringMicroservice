package com.mindtree.customer.controller;

import java.time.LocalDateTime;

//import java.time.LocalDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.customer.service.CustomerService;
//import com.mindtree.customer.utility.ErrorInfo;
import com.mindtree.customer.utility.ErrorInfo;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import com.mindtree.customer.dto.CustomerDTO;
import com.mindtree.customer.exception.CustomerException;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

	@Autowired
	Environment environment;

	@Autowired
	private CustomerService customerService;

	final Log LOGGER = LogFactory.getLog(CustomerController.class);

//	@CircuitBreaker(name = "customerService",fallbackMethod = "fallBackResponse")
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createCustomer(@RequestBody CustomerDTO custDTO)
			throws CustomerException {
		Long phoneNo = customerService.createCustomer(custDTO);
		String message = environment.getProperty("Service.PROFILE_CREATED") + phoneNo;
		LOGGER.info(message);
		return new ResponseEntity<String>(message, HttpStatus.CREATED);
	}

	@CircuitBreaker(name = "customerService", fallbackMethod = "fallBackResponse")
	@GetMapping(value = "/find/{phoneNo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findCustomer( @PathVariable Long phoneNo)
			throws CustomerException {
		CustomerDTO data = customerService.getCustomer(phoneNo);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	public ResponseEntity<Object> fallBackResponse(Long phoneNo, Throwable throwable) {
		LOGGER.error(environment.getProperty("General.EXCEPTION_MESSAGE"));
		ErrorInfo error=new ErrorInfo();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setErrorMsg(environment.getProperty("General.EXCEPTION_MESSAGE"));
		error.setTimeStamp(LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
