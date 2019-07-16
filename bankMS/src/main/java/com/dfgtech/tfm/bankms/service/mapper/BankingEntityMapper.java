package com.dfgtech.tfm.bankms.service.mapper;

import com.dfgtech.tfm.bankms.domain.*;
import com.dfgtech.tfm.bankms.service.dto.BankingEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankingEntity} and its DTO {@link BankingEntityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BankingEntityMapper extends EntityMapper<BankingEntityDTO, BankingEntity> {



    default BankingEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        BankingEntity bankingEntity = new BankingEntity();
        bankingEntity.setId(id);
        return bankingEntity;
    }
}
