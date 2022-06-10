package com.mindtree.friendfamily.controller;

//import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.mindtree.friendfamily.utility.ErrorInfo;
import com.mindtree.friendfamily.dto.FriendFamilyDTO;
import com.mindtree.friendfamily.exception.FriendFamilyException;
import com.mindtree.friendfamily.service.FriendFamilyService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "/friendfamily")
public class FrienFamilyController {

	@Autowired
	private FriendFamilyService familyService;

	@Autowired
	Environment environment;

	final Log LOGGER = LogFactory.getLog(FrienFamilyController.class);
	@CircuitBreaker(name = "friendFamilyService",fallbackMethod = "fallBackResponse")
	@PostMapping(value = "/save/{phoneNo}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveFriend(@PathVariable Long phoneNo, @RequestBody FriendFamilyDTO friendDTO)
			throws FriendFamilyException {
		familyService.saveFriend(phoneNo, friendDTO);
		String message = environment.getProperty("");
		LOGGER.info(message);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	@GetMapping(value = "/fetch/{phoneNo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> getFriends(@PathVariable Long phoneNo)
			throws FriendFamilyException {
		List<Long>friends=familyService.getFriends(phoneNo);
		String message = environment.getProperty("");
		LOGGER.info(message +friends);
		return new ResponseEntity<>(friends, HttpStatus.OK);
	}
	public ResponseEntity<String> fallBackResponse(Long phoneNo,FriendFamilyDTO friendDTO,Throwable throwable)
	{
		LOGGER.error(environment.getProperty("General.EXCEPTION_MESSAGE"));
//		ErrorInfo error=new ErrorInfo();
//		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//		error.setErrorMsg(environment.getProperty("General.EXCEPTION_MESSAGE"));
//		error.setTimeStamp(LocalDateTime.now());
		return new ResponseEntity<>(environment.getProperty("General.EXCEPTION_MESSAGE"), HttpStatus.OK);
		
	}
}
