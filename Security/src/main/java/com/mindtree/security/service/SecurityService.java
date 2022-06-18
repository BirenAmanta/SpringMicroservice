package com.mindtree.security.service;

import com.mindtree.security.exception.SecurityException;
import com.mindtree.security.dto.LoginDTO;
import com.mindtree.security.dto.TokenDTO;


public interface SecurityService {
	public TokenDTO login(LoginDTO login)throws SecurityException ;
}
