package com.dfgtech.tfm.creditapp.service;

import com.dfgtech.tfm.creditapp.domain.Customer;
import com.dfgtech.tfm.creditapp.domain.User;
import com.dfgtech.tfm.creditapp.domain.enumeration.IdentificationType;
import com.dfgtech.tfm.creditapp.repository.CustomerRepository;
import com.dfgtech.tfm.creditapp.security.SecurityUtils;
import com.dfgtech.tfm.creditapp.service.dto.CustomerDTO;
import com.dfgtech.tfm.creditapp.service.mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Customer}.
 */
@Service
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomerDTO save(CustomerDTO customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);
        Customer customer = customerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    /**
     * Get all the customers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAll() {
        log.debug("Request to get all Customers");
        return customerRepository.findAll().stream()
            .map(customerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerDTO> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id)
            .map(customerMapper::toDto);
    }
    
    /**
     * Get one customer by user login.
     *
     * @param user the login of the current authenticated user.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerDTO> findByUserLogin(String login) {
        log.debug("Request to get Customer : {}", login);
        return customerRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get())
        		.map(customerMapper::toDto);
    }
    
    /**
     * Get one customer by customer identification.
     *
     * @param identificationType the identification type of the requested customer.
     * @param identificationNumber the identification number of the requested customer.
     * @param country the identification country of the requested customer.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerDTO> findByIdentification(String identificationType, String identificationNumber, String country) {
        log.debug("Request to get Customer : {} - {} - {}", identificationType, identificationNumber, country);
        return customerRepository.findByIdentification(IdentificationType.valueOf(identificationType),identificationNumber,country)
        		.map(customerMapper::toDto);
    }

    /**
     * Delete the customer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }
}
