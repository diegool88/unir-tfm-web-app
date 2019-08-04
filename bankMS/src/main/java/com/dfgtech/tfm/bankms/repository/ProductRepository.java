package com.dfgtech.tfm.bankms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dfgtech.tfm.bankms.domain.Product;


/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p FROM Product p INNER JOIN p.bankingEntity b WHERE b.id = ?1")
	Page<Product> findByBankingEntity(Long bankingEntity, Pageable pageable);
	
	@Query("SELECT p FROM Product p INNER JOIN p.bankingEntity b WHERE b.mnemonic = ?1")
	Page<Product> findByBankingEntityMnemonic(String bankingEntityMnemonic, Pageable pageable);
}
