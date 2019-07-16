package com.dfgtech.tfm.loanms.service.mapper;

import com.dfgtech.tfm.loanms.domain.*;
import com.dfgtech.tfm.loanms.service.dto.WarrantyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Warranty} and its DTO {@link WarrantyDTO}.
 */
@Mapper(componentModel = "spring", uses = {LoanProcessMapper.class})
public interface WarrantyMapper extends EntityMapper<WarrantyDTO, Warranty> {


    @Mapping(target = "removeLoanProcess", ignore = true)

    default Warranty fromId(Long id) {
        if (id == null) {
            return null;
        }
        Warranty warranty = new Warranty();
        warranty.setId(id);
        return warranty;
    }
}
