package com.dfgtech.tfm.bankms.repository;

import com.dfgtech.tfm.bankms.domain.Fee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Fee entity.
 */
@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {

    @Query(value = "select distinct fee from Fee fee left join fetch fee.products",
        countQuery = "select count(distinct fee) from Fee fee")
    Page<Fee> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct fee from Fee fee left join fetch fee.products")
    List<Fee> findAllWithEagerRelationships();

    @Query("select fee from Fee fee left join fetch fee.products where fee.id =:id")
    Optional<Fee> findOneWithEagerRelationships(@Param("id") Long id);

}
