package com.dfgtech.tfm.creditapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dfgtech.tfm.creditapp.domain.Customer;
import com.dfgtech.tfm.creditapp.domain.enumeration.IdentificationType;


/**
 * Spring Data  repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByUserLogin(String login);
	
	@Query("SELECT c FROM Customer c WHERE c.identificationType = ?1 AND c.identificationNumber = ?2 AND c.country = ?3")
	Optional<Customer> findByIdentification(IdentificationType identificationType, String identificationNumber, String country);
}
