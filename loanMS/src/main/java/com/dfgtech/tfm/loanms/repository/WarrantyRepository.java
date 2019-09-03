package com.dfgtech.tfm.loanms.repository;

import com.dfgtech.tfm.loanms.domain.Warranty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Warranty entity.
 */
@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Long> {

    @Query(value = "select distinct warranty from Warranty warranty left join fetch warranty.loanProcesses",
        countQuery = "select count(distinct warranty) from Warranty warranty")
    Page<Warranty> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct warranty from Warranty warranty left join fetch warranty.loanProcesses")
    List<Warranty> findAllWithEagerRelationships();

    @Query("select warranty from Warranty warranty left join fetch warranty.loanProcesses where warranty.id =:id")
    Optional<Warranty> findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select distinct warranty from Warranty warranty left join fetch warranty.loanProcesses loanProcess where loanProcess.id =:id")
    List<Warranty> findAllWarrantiesByLoanProcessId(@Param("id") Long id);

}
