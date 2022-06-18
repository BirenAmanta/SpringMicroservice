package com.mindtree.customer.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "FriendFamilyMS")
public interface CustomerFriendFamilyFeign {
	@RequestMapping(path = "friendfamily/fetch/{customerPhoneNo}")
	public List<Long> getFriendFeign(@PathVariable Long customerPhoneNo);
}
