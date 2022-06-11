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
	
	@CircuitBreaker(name = "customerService")
	@Override
	public Future<PlanDTO> getPlan(Integer planId) {
		// TODO Auto-generated method stub
		return Future.of(()->restTemplate.getForObject("http://PlanMS/plan/{planId}", PlanDTO.class,planId)); 
	}

	@SuppressWarnings("unchecked")
	@Override
	@CircuitBreaker(name = "customerService")
	public Future<List<Long>> getFriends(Long customerPhoneNo) {
		// TODO Auto-generated method stub
		return  Future.of(()->(List<Long>) restTemplate
				.getForObject("http://FriendFamilyMS/friendfamily/fetch/{customerPhoneNo}", List.class,customerPhoneNo));
	}

}
