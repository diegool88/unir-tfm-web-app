package com.dfgtech.tfm.bankms.service.mapper;

import com.dfgtech.tfm.bankms.domain.*;
import com.dfgtech.tfm.bankms.service.dto.BankingAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankingAccount} and its DTO {@link BankingAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BankingAccountMapper extends EntityMapper<BankingAccountDTO, BankingAccount> {



    default BankingAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        BankingAccount bankingAccount = new BankingAccount();
        bankingAccount.setId(id);
        return bankingAccount;
    }
}
