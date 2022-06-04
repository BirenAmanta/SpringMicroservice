package com.mindtree.plan.repository;

import org.springframework.data.repository.CrudRepository;

import com.mindtree.plan.entity.PlanEntity;

public interface PlanRepository extends CrudRepository<PlanEntity, Integer> {

}
