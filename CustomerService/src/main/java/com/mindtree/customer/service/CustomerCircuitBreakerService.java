package com.mindtree.customer.service;

import java.util.List;

import com.mindtree.customer.dto.PlanDTO;

import io.vavr.concurrent.Future;

public interface CustomerCircuitBreakerService {

	public Future<PlanDTO> getPlan(Integer planId);
	public Future<List<Long>> getFriends(Long customerPhoneNo);
}
