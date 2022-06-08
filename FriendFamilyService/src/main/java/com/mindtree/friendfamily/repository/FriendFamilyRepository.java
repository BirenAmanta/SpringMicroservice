package com.mindtree.friendfamily.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mindtree.friendfamily.entity.FriendFamily;

public interface FriendFamilyRepository extends CrudRepository<FriendFamily, Integer> {
	List<FriendFamily> findByPhoneNo(Long phoneNo);
}
