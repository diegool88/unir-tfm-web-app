package com.dfgtech.tfm.bankms.service.mapper;

import com.dfgtech.tfm.bankms.domain.*;
import com.dfgtech.tfm.bankms.service.dto.FeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fee} and its DTO {@link FeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface FeeMapper extends EntityMapper<FeeDTO, Fee> {


    @Mapping(target = "removeProduct", ignore = true)

    default Fee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fee fee = new Fee();
        fee.setId(id);
        return fee;
    }
}
