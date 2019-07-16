package com.dfgtech.tfm.creditapp.repository;

import com.dfgtech.tfm.creditapp.domain.PersonalReference;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonalReference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalReferenceRepository extends JpaRepository<PersonalReference, Long> {

}
