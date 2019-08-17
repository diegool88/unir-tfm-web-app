package com.dfgtech.tfm.bankms.external.service;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dfgtech.tfm.bankms.external.service.dto.CustomerDTO;

@FeignClient(name = "creditapp")
public interface CustomerServiceClient {
	@RequestMapping(method = RequestMethod.GET, value = "/api/customers")
    List<CustomerDTO> getLoggedCustomer();
}
