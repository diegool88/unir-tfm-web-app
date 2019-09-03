package com.dfgtech.tfm.bankms.repository;

import com.dfgtech.tfm.bankms.domain.BankingEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BankingEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankingEntityRepository extends JpaRepository<BankingEntity, Long> {
	Optional<BankingEntity> findByMnemonic(String mnemonic);
}
