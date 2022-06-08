package com.mindtree.customer.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Customer {

	@Id
	private Long phoneNo;
	private String name;
	private Integer age;
	private String address;
	private String password;
	private char gender;
	private Integer planId;
}
