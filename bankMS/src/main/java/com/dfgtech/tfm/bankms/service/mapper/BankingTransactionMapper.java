package com.dfgtech.tfm.bankms.service.mapper;

import com.dfgtech.tfm.bankms.domain.*;
import com.dfgtech.tfm.bankms.service.dto.BankingTransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankingTransaction} and its DTO {@link BankingTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {BankingAccountMapper.class, BankingEntityMapper.class})
public interface BankingTransactionMapper extends EntityMapper<BankingTransactionDTO, BankingTransaction> {

    @Mapping(source = "originAccount.id", target = "originAccountId")
    @Mapping(source = "originAccount.number", target = "originAccountNumber")
    @Mapping(source = "destinationAccount.id", target = "destinationAccountId")
    @Mapping(source = "destinationAccount.number", target = "destinationAccountNumber")
    @Mapping(source = "bankingEntity.id", target = "bankingEntityId")
    BankingTransactionDTO toDto(BankingTransaction bankingTransaction);

    @Mapping(source = "originAccountId", target = "originAccount")
    @Mapping(source = "destinationAccountId", target = "destinationAccount")
    @Mapping(source = "bankingEntityId", target = "bankingEntity")
    BankingTransaction toEntity(BankingTransactionDTO bankingTransactionDTO);

    default BankingTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        BankingTransaction bankingTransaction = new BankingTransaction();
        bankingTransaction.setId(id);
        return bankingTransaction;
    }
}
