package com.dfgtech.tfm.bankms.repository;

import com.dfgtech.tfm.bankms.domain.BankingAccount;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BankingAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankingAccountRepository extends JpaRepository<BankingAccount, Long> {

	@Query("SELECT ba FROM BankingAccount ba WHERE ba.customerIdentification = ?1 AND ba.customerIdentificationType = ?2 AND ba.customerCountry = ?3")
	List<BankingAccount> findByCustomer(String customerIdentification, String customerIdentificationType, String customerCountry);
}
