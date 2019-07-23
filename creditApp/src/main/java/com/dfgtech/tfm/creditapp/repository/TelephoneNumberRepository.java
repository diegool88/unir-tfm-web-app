package com.dfgtech.tfm.creditapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dfgtech.tfm.creditapp.domain.TelephoneNumber;


/**
 * Spring Data  repository for the TelephoneNumber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelephoneNumberRepository extends JpaRepository<TelephoneNumber, Long> {
	@Query("SELECT t FROM TelephoneNumber t inner join t.address a inner join a.customer c WHERE c.id = ?1")
	Page<TelephoneNumber> findByCustomerId(Long customer, Pageable pageable);
}
