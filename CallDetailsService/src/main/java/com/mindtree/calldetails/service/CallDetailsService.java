package com.mindtree.calldetails.service;

import java.util.List;

import com.mindtree.calldetails.exception.CallDetailsException;
import com.mindtree.calldetails.dto.CallDetailsDTO;

public interface CallDetailsService {
	public List<CallDetailsDTO> getCustomerCallDetails(Long phoneNo) throws CallDetailsException;
}
