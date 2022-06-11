package com.mindtree.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;

import com.mindtree.customer.dto.CustomerDTO;
import com.mindtree.customer.dto.PlanDTO;
import com.mindtree.customer.entity.Customer;
import com.mindtree.customer.exception.CustomerException;
import com.mindtree.customer.repository.CustomerRepository;
import com.mindtree.customer.utility.Conversion;

import io.vavr.concurrent.Future;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerCircuitBreakerService breakerService;

	@Autowired
	private CustomerRepository customerRepository;

//	@Autowired
//	private DiscoveryClient client;

	@Override
	public Long createCustomer(CustomerDTO custDTO) throws CustomerException {
//		List<ServiceInstance> planUrls = client.getInstances("PlanMS");
//		String planUrl = "";
//		if (!planUrls.isEmpty() && planUrls != null)
//			planUrl = planUrls.get(0).getUri().toString();
//		else
//			throw new CustomerException("");
		Future<PlanDTO> plans = breakerService.getPlan(custDTO.getPlan().getPlanId());
		if (plans == null) {
			throw new CustomerException("");
		}
		Customer customer = new Conversion<CustomerDTO, Customer>().getConvertedObject(custDTO, Customer.class);
		customer.setPlanId(plans.get().getPlanId());
		customerRepository.save(customer);
		return custDTO.getPhoneNo();
	}

	@Override
	public CustomerDTO getCustomer(Long phoneNo) throws CustomerException {
//		List<ServiceInstance> planUrls = client.getInstances("PlanMS");
//		String planUrl = "";
//		if (!planUrls.isEmpty() && planUrls != null)
//			planUrl = planUrls.get(0).getUri().toString();
//		else
//			throw new CustomerException("");

		Optional<Customer> data = customerRepository.findById(phoneNo);
		Customer cust = data.orElseThrow(() -> new CustomerException("Not found"));
		System.out.println(cust);
		Future<PlanDTO> plans = breakerService.getPlan(cust.getPlanId());
//		List<ServiceInstance> friendFamilyUrls = client.getInstances("FriendFamilyMS");
//		String friendFamilyUrl = "";
//		if (!friendFamilyUrls.isEmpty() && friendFamilyUrls != null)
//			friendFamilyUrl = friendFamilyUrls.get(0).getUri().toString();
//		else
//			throw new CustomerException("");
		Future<List<Long>> friendfamily = breakerService.getFriends(cust.getPhoneNo());
		CustomerDTO responseData = new Conversion<Customer, CustomerDTO>().getConvertedObject(cust, CustomerDTO.class);
		responseData.setFriends(friendfamily.get());
		responseData.setPlan(plans.get());
		return responseData;
	}

}
