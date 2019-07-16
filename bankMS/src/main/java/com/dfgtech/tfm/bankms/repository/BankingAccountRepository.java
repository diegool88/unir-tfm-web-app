package com.dfgtech.tfm.bankms.repository;

import com.dfgtech.tfm.bankms.domain.BankingAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BankingAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankingAccountRepository extends JpaRepository<BankingAccount, Long> {

}
