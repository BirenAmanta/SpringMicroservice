package com.mindtree.calldetails.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mindtree.calldetails.entity.CallDetails;

public interface CallDetailsRepository extends CrudRepository<CallDetails, Integer> {
	List<CallDetails> findByCalledBy(Long phoneNo);
}
