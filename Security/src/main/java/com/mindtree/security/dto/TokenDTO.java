package com.mindtree.security.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {

	private String token;
	private LocalDate timestamp; 

}
