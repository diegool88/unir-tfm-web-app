package com.dfgtech.tfm.loanms.repository;

import com.dfgtech.tfm.loanms.domain.AmortizationTable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AmortizationTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizationTableRepository extends JpaRepository<AmortizationTable, Long> {

}
