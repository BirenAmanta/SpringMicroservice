package com.mindtree.customer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mindtree.customer.dto.PlanDTO;

@FeignClient(name="PlanMs",url="http://localhost:5001")
public interface CustomerPlanFeign {

	@RequestMapping(path = "/plan/{planId}")
	public PlanDTO getPlanFeign(@PathVariable Integer planId);
}
