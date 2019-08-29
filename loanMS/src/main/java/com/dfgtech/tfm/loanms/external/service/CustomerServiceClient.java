package com.dfgtech.tfm.loanms.external.service;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dfgtech.tfm.loanms.external.service.dto.CustomerDTO;

@FeignClient(name = "creditapp")
public interface CustomerServiceClient {
	@RequestMapping(method = RequestMethod.GET, value = "/api/customers")
    List<CustomerDTO> getLoggedCustomer();
}
