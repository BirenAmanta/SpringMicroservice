package com.mindtree.friendfamily.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mindtree.friendfamily.dto.CustomerDTO;
import com.mindtree.friendfamily.dto.FriendFamilyDTO;
import com.mindtree.friendfamily.entity.FriendFamily;
import com.mindtree.friendfamily.exception.FriendFamilyException;
import com.mindtree.friendfamily.repository.FriendFamilyRepository;
import com.mindtree.friendfamily.utility.Conversion;;

@Service
public class FriendFamilyServiceImpl implements FriendFamilyService {

	@Autowired
	private FriendFamilyRepository familyRepository;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient client;

	@Override
	public void saveFriend(Long phoneNo, FriendFamilyDTO friendDTO) throws FriendFamilyException {
		List<ServiceInstance> customerUrls = client.getInstances("CustomerMS");
		String customerUrl = "";
		if (!customerUrls.isEmpty() && customerUrls != null)
			customerUrl = customerUrls.get(0).getUri().toString();
		else
			throw new FriendFamilyException("");
		CustomerDTO data = restTemplate.getForObject(customerUrl+"/customer/find/{phoneno}", CustomerDTO.class,
				friendDTO.getFriendAndFamily());
		if (data == null) {
			throw new FriendFamilyException("");
		}
		friendDTO.setPhoneNo(phoneNo);
		familyRepository.save(
				new Conversion<FriendFamilyDTO, FriendFamily>().getConvertedObject(friendDTO, FriendFamily.class));

	}

	@Override
	public List<Long> getFriends(Long phoneNo) throws FriendFamilyException {
		List<FriendFamily>data=familyRepository.findByPhoneNo(phoneNo);
		if(data.isEmpty())
		{
			throw new FriendFamilyException("");
		}
		
		return data.stream().map((temp)->temp.getFriendAndFamily()).collect(Collectors.toList());
	}

}
