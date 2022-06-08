package com.mindtree.friendfamily.service;

import java.util.List;

import com.mindtree.friendfamily.dto.FriendFamilyDTO;
import com.mindtree.friendfamily.exception.FriendFamilyException;

public interface FriendFamilyService {
	public void saveFriend(Long phoneNo, FriendFamilyDTO friendDTO) throws FriendFamilyException;

	public List<Long> getFriends(Long phoneNo) throws FriendFamilyException;
}
