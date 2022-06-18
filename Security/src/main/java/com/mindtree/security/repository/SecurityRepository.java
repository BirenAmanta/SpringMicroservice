package com.mindtree.security.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mindtree.security.entity.Customer;

public interface SecurityRepository extends CrudRepository<Customer, Long> {

	Optional<Customer> findByName(String username);
}
