package com.dfgtech.tfm.creditapp.repository;

import com.dfgtech.tfm.creditapp.domain.Customer;
import com.dfgtech.tfm.creditapp.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByUserLogin(Optional<User> user);
}
