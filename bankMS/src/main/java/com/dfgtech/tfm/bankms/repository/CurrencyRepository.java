package com.dfgtech.tfm.bankms.repository;

import com.dfgtech.tfm.bankms.domain.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Currency entity.
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Query(value = "select distinct currency from Currency currency left join fetch currency.products",
        countQuery = "select count(distinct currency) from Currency currency")
    Page<Currency> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct currency from Currency currency left join fetch currency.products")
    List<Currency> findAllWithEagerRelationships();

    @Query("select currency from Currency currency left join fetch currency.products where currency.id =:id")
    Optional<Currency> findOneWithEagerRelationships(@Param("id") Long id);

}
