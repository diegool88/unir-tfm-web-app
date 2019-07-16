package com.dfgtech.tfm.loanms.service.mapper;

import com.dfgtech.tfm.loanms.domain.*;
import com.dfgtech.tfm.loanms.service.dto.AmortizationTableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AmortizationTable} and its DTO {@link AmortizationTableDTO}.
 */
@Mapper(componentModel = "spring", uses = {LoanProcessMapper.class})
public interface AmortizationTableMapper extends EntityMapper<AmortizationTableDTO, AmortizationTable> {

    @Mapping(source = "loanProcess.id", target = "loanProcessId")
    AmortizationTableDTO toDto(AmortizationTable amortizationTable);

    @Mapping(source = "loanProcessId", target = "loanProcess")
    AmortizationTable toEntity(AmortizationTableDTO amortizationTableDTO);

    default AmortizationTable fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmortizationTable amortizationTable = new AmortizationTable();
        amortizationTable.setId(id);
        return amortizationTable;
    }
}
