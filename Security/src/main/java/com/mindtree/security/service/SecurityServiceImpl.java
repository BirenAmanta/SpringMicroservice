package com.mindtree.security.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.security.exception.SecurityException;
import com.mindtree.security.repository.SecurityRepository;
import com.mindtree.security.utility.SecurityUtility;
import com.mindtree.security.dto.LoginDTO;
import com.mindtree.security.dto.TokenDTO;
import com.mindtree.security.entity.Customer;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private SecurityUtility loginUtility;

	@Autowired
	SecurityRepository customerRepository;

	@Override
	public TokenDTO login(LoginDTO login) throws SecurityException {
		TokenDTO token = new TokenDTO();
		Optional<Customer>data=customerRepository.findByName(login.getUsername());
		data.orElseThrow(()->new SecurityException("Username Invalid"));
		String tokenValue = loginUtility.generateToken(login.getUsername());
		System.out.println(tokenValue);
		token.setTimestamp(LocalDate.now());
		token.setToken(tokenValue);
		return token;
	}

	

}
