package com.dfgtech.tfm.bankms.repository;

import com.dfgtech.tfm.bankms.domain.BankingAccount;
import com.dfgtech.tfm.bankms.domain.enumeration.AccountType;

import java.util.List;
import java.util.Optional;

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
	
	@Query("SELECT ba FROM BankingAccount ba WHERE ba.number = ?1 AND ba.accountType = ?2 AND ba.bankingEntityMnemonic = ?3")
	Optional<BankingAccount> findByAccountNumber(Integer number, AccountType accountType, String bankingEntityMnemonic);
}
