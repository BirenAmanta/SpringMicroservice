package com.mindtree.apigateway.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ValidateDTO {

	private Boolean validationStatus;
	private Integer statusCode;
}
