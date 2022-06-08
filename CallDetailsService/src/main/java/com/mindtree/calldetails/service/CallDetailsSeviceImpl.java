package com.mindtree.calldetails.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.calldetails.dto.CallDetailsDTO;
import com.mindtree.calldetails.exception.CallDetailsException;
import com.mindtree.calldetails.repository.CallDetailsRepository;
import com.mindtree.calldetails.entity.CallDetails;
import com.mindtree.calldetails.utility.Conversion;

@Service
public class CallDetailsSeviceImpl implements CallDetailsService {

	@Autowired
	private CallDetailsRepository callDetailsRepository;
	@Override
	public List<CallDetailsDTO> getCustomerCallDetails(Long phoneNo) throws CallDetailsException {
		List<CallDetails> details = callDetailsRepository.findByCalledBy(phoneNo);
		if (details.isEmpty()) {
			throw new CallDetailsException("Service.CALLDETAILS_NOT_FOUND");
		}
		return details.stream().map(
				(data) -> new Conversion<CallDetails, CallDetailsDTO>().getConvertedObject(data, CallDetailsDTO.class))
				.collect(Collectors.toList());
	}

}
