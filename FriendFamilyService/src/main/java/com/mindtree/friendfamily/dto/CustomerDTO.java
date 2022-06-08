package com.mindtree.friendfamily.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {

	private Long phoneNo;
	private String name;
	private Integer age;
	private String address;
	private String password;
	private char gender;
	private Integer planId;
}
