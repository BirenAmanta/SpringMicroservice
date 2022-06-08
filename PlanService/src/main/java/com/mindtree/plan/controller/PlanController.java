package com.mindtree.plan.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.mindtree.plan.dto.PlanDTO;
import com.mindtree.plan.exception.PlanException;
import com.mindtree.plan.service.PlanService;

@RestController
@RequestMapping(value = "/plan")
public class PlanController {

	@Autowired
	private PlanService planService;
	final Log LOGGER = LogFactory.getLog(PlanController.class);
	
	@Autowired
	Environment environment;
	@GetMapping(value = "/plans", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlanDTO>> getAllPlans() throws PlanException {
		List<PlanDTO>allPlans=planService.getAllPlans();
		String message = environment.getProperty("");
		LOGGER.info(message);
		return new ResponseEntity<List<PlanDTO>>(allPlans,HttpStatus.OK);
	}
	@GetMapping(value = "/{planId}" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlanDTO> getPlan(@PathVariable Integer planId) throws PlanException
	{
		PlanDTO plan=planService.getPlan(planId);
		String message = environment.getProperty("");
		LOGGER.info(message);
		return new ResponseEntity<PlanDTO>(plan,HttpStatus.OK);
	}
	
}
