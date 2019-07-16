package com.dfgtech.tfm.creditapp.repository;

import com.dfgtech.tfm.creditapp.domain.TelephoneNumber;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TelephoneNumber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelephoneNumberRepository extends JpaRepository<TelephoneNumber, Long> {

}
