package com.mindtree.plan.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlanDTO {

	private Integer planId;
	private String planName;
	private Integer nationalRate;
	private Integer localRate;
}
