package com.dfgtech.tfm.creditapp.repository;

import com.dfgtech.tfm.creditapp.domain.PersonalReference;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonalReference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalReferenceRepository extends JpaRepository<PersonalReference, Long> {
	Page<PersonalReference> findByCustomerId(Long customer, Pageable pageable);
}
