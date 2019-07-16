package com.dfgtech.tfm.loanms.service.mapper;

import com.dfgtech.tfm.loanms.domain.*;
import com.dfgtech.tfm.loanms.service.dto.LoanProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoanProcess} and its DTO {@link LoanProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LoanProcessMapper extends EntityMapper<LoanProcessDTO, LoanProcess> {


    @Mapping(target = "warranties", ignore = true)
    @Mapping(target = "removeWarranties", ignore = true)
    LoanProcess toEntity(LoanProcessDTO loanProcessDTO);

    default LoanProcess fromId(Long id) {
        if (id == null) {
            return null;
        }
        LoanProcess loanProcess = new LoanProcess();
        loanProcess.setId(id);
        return loanProcess;
    }
}
