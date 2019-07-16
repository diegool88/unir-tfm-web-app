package com.dfgtech.tfm.bankms.service.mapper;

import com.dfgtech.tfm.bankms.domain.*;
import com.dfgtech.tfm.bankms.service.dto.CurrencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Currency} and its DTO {@link CurrencyDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CurrencyMapper extends EntityMapper<CurrencyDTO, Currency> {


    @Mapping(target = "removeProduct", ignore = true)

    default Currency fromId(Long id) {
        if (id == null) {
            return null;
        }
        Currency currency = new Currency();
        currency.setId(id);
        return currency;
    }
}
