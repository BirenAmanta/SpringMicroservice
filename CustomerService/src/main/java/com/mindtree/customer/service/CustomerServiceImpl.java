package com.mindtree.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mindtree.customer.dto.CustomerDTO;
import com.mindtree.customer.dto.PlanDTO;
import com.mindtree.customer.entity.Customer;
import com.mindtree.customer.exception.CustomerException;
import com.mindtree.customer.repository.CustomerRepository;
import com.mindtree.customer.utility.Conversion;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private DiscoveryClient client;

	@Override
	public Long createCustomer(CustomerDTO custDTO) throws CustomerException {
		List<ServiceInstance> planUrls = client.getInstances("PlanMS");
		String planUrl = "";
		if (!planUrls.isEmpty() && planUrls != null)
			planUrl = planUrls.get(0).getUri().toString();
		else
			throw new CustomerException("");
		PlanDTO plans = restTemplate.getForObject(planUrl + "/plan/{id}", PlanDTO.class, custDTO.getPlan().getPlanId());
		if (plans == null) {
			throw new CustomerException("");
		}
		Customer customer = new Conversion<CustomerDTO, Customer>().getConvertedObject(custDTO, Customer.class);
		customer.setPlanId(plans.getPlanId());
		customerRepository.save(customer);
		return custDTO.getPhoneNo();
	}

	@Override
	public CustomerDTO getCustomer(Long phoneNo) throws CustomerException {
		List<ServiceInstance> planUrls = client.getInstances("PlanMS");
		String planUrl = "";
		if (!planUrls.isEmpty() && planUrls != null)
			planUrl = planUrls.get(0).getUri().toString();
		else
			throw new CustomerException("");

		Optional<Customer> data = customerRepository.findById(phoneNo);
		Customer cust = data.orElseThrow(() -> new CustomerException("Not found"));
		System.out.println(cust);
		PlanDTO plans = restTemplate.getForObject(planUrl + "/plan/" + cust.getPlanId(), PlanDTO.class);
		List<ServiceInstance> friendFamilyUrls = client.getInstances("FriendFamilyMS");
		String friendFamilyUrl = "";
		if (!friendFamilyUrls.isEmpty() && friendFamilyUrls != null)
			friendFamilyUrl = friendFamilyUrls.get(0).getUri().toString();
		else
			throw new CustomerException("");
		@SuppressWarnings("unchecked")
		List<Long> friendfamily = (List<Long>) restTemplate
				.getForObject(friendFamilyUrl + "/friendfamily/fetch/" + cust.getPhoneNo(), List.class);
		CustomerDTO responseData = new Conversion<Customer, CustomerDTO>().getConvertedObject(cust, CustomerDTO.class);
		responseData.setFriends(friendfamily);
		responseData.setPlan(plans);
		return responseData;
	}

}
