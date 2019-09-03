package com.dfgtech.tfm.creditapp.repository;

import com.dfgtech.tfm.creditapp.domain.Address;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	Page<Address> findByCustomerId(Long customer, Pageable pageable);
}
