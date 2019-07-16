package com.dfgtech.tfm.bankms.repository;

import com.dfgtech.tfm.bankms.domain.BankingTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BankingTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankingTransactionRepository extends JpaRepository<BankingTransaction, Long> {

}
