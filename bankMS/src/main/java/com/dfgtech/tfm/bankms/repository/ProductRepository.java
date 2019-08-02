package com.dfgtech.tfm.bankms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dfgtech.tfm.bankms.domain.Product;


/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findByBankingEntity(Long bankingEntity, Pageable pageable);
}
