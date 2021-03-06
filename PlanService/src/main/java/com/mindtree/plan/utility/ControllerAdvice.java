package com.mindtree.plan.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.mindtree.plan.exception.PlanException;
import com.mindtree.plan.utility.ControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

	static final Log LOGGER = LogFactory.getLog(ControllerAdvice.class);

	@Autowired
	Environment environment;


	@ExceptionHandler(PlanException.class)
	public ResponseEntity<ErrorInfo> CustomerException(PlanException exception) {
		String message = environment.getProperty(exception.getMessage());
		LOGGER.error(message);
		ErrorInfo error = new ErrorInfo();
		error.setErrorMsg(message);
		error.setTimeStamp(LocalDateTime.now());
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorInfo> pathException(NoHandlerFoundException exception) {
		String message = environment.getProperty(exception.getMessage(),exception.getMessage());
		LOGGER.error(message);
		ErrorInfo error = new ErrorInfo();
		error.setErrorMsg(message);
		error.setTimeStamp(LocalDateTime.now());
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
	public ResponseEntity<ErrorInfo> validateException(Exception exception) {
		String message;
		if (exception instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException expt = (MethodArgumentNotValidException) exception;
			message = expt.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(","));
		} else {
			ConstraintViolationException expt = (ConstraintViolationException) exception;
			message = expt.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.joining(","));
		}
		ErrorInfo error=new ErrorInfo();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setErrorMsg(message);
		error.setTimeStamp(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(error,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generalException(Exception e) {
		System.out.println(e.getMessage());
		LOGGER.error(e.getMessage());
		String message = environment.getProperty(e.getMessage(), "General.EXCEPTION_MESSAGE");
		ErrorInfo error = new ErrorInfo();
		error.setErrorMsg(message);
		error.setTimeStamp(LocalDateTime.now());
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}