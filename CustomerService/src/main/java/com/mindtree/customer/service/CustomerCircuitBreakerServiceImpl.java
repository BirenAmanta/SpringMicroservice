package com.mindtree.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mindtree.customer.dto.PlanDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.vavr.concurrent.Future;

@Service
public class CustomerCircuitBreakerServiceImpl implements CustomerCircuitBreakerService {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CustomerFriendFamilyFeign customerFriendFamilyFeign;
	
	@Autowired
	CustomerPlanFeign customerPlanFeign;
	
	@CircuitBreaker(name = "customerService")
	@Override
	public Future<PlanDTO> getPlan(Integer planId) {
		// TODO Auto-generated method stub
		return Future.of(()->customerPlanFeign.getPlanFeign(planId)); 
	}

//	@SuppressWarnings("unchecked")
	@Override
	@CircuitBreaker(name = "customerService")
	public Future<List<Long>> getFriends(Long customerPhoneNo) {
		// TODO Auto-generated method stub
		return  Future.of(()->customerFriendFamilyFeign.getFriendFeign(customerPhoneNo));
	}

}
