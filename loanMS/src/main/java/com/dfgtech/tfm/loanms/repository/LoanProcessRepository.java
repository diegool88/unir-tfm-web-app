package com.dfgtech.tfm.loanms.repository;

import com.dfgtech.tfm.loanms.domain.LoanProcess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LoanProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanProcessRepository extends JpaRepository<LoanProcess, Long> {

}
