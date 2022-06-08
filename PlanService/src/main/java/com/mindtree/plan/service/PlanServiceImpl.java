package com.mindtree.plan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.plan.entity.Plan;
import com.mindtree.plan.utility.Conversion;
import com.mindtree.plan.dto.PlanDTO;
import com.mindtree.plan.exception.PlanException;
import com.mindtree.plan.repository.PlanRepository;

@Service
public class PlanServiceImpl implements PlanService {


	@Autowired
	private PlanRepository planRepository;

	@Override
	public List<PlanDTO> getAllPlans() throws PlanException {
		
		Iterable<Plan> plans = planRepository.findAll();
		List<PlanDTO> data = new ArrayList<>();
		plans.forEach((temp) ->

		data.add(new Conversion<Plan, PlanDTO>().getConvertedObject(temp, PlanDTO.class)));
		if (data.isEmpty()) {
			throw new PlanException("Service.PLANS_NOT_FOUND");
		}
		return data;
	}
	@Override
	public PlanDTO getPlan(Integer planId) throws PlanException {
		
		Optional<Plan> plans = planRepository.findById(planId);
		Plan plan = plans.orElseThrow(() -> new PlanException("Service.PLAN_NOT_FOUND"));
		PlanDTO data=new Conversion<Plan,PlanDTO>().getConvertedObject(plan,PlanDTO.class);
		return data;
	}

}
