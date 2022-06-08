package com.mindtree.plan.repository;

import org.springframework.data.repository.CrudRepository;

import com.mindtree.plan.entity.Plan;

public interface PlanRepository extends CrudRepository<Plan, Integer> {

}
