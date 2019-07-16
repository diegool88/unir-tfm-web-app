package com.dfgtech.tfm.regionms.repository;

import com.dfgtech.tfm.regionms.domain.StateProvince;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StateProvince entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateProvinceRepository extends JpaRepository<StateProvince, Long> {

}
