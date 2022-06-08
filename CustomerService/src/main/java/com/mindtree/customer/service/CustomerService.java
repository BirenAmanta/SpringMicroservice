package com.mindtree.customer.service;

import com.mindtree.customer.dto.CustomerDTO;
import com.mindtree.customer.exception.CustomerException;

public interface CustomerService {
	
	public Long createCustomer(CustomerDTO custDTO) throws CustomerException;
	public CustomerDTO getCustomer(Long phoneNo) throws CustomerException;

}
