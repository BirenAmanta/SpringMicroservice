package com.mindtree.plan.service;

import java.util.List;

import com.mindtree.plan.dto.PlanDTO;
import com.mindtree.plan.exception.PlanException;

public interface PlanService {
	public List<PlanDTO> getAllPlans() throws PlanException;

	PlanDTO getPlan(Integer planId) throws PlanException;
}
