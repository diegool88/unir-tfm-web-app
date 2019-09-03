package com.dfgtech.tfm.loanms.repository;

import com.dfgtech.tfm.loanms.domain.LoanProcess;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LoanProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanProcessRepository extends JpaRepository<LoanProcess, Long> {
	@Query("SELECT lp FROM LoanProcess lp WHERE lp.debtorIdentification = ?1 AND lp.debtorIdentificationType = ?2 AND lp.debtorCountry = ?3")
	List<LoanProcess> findByCustomer(String debtorIdentification, String debtorIdentificationType, String debtorCountry);
}
