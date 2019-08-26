package com.dfgtech.tfm.loanms.service.mapper;

import com.dfgtech.tfm.loanms.domain.LoanProcess;
import com.dfgtech.tfm.loanms.service.dto.LoanProcessDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-08-25T20:50:43-0500",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.17.0.v20190306-2240, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class LoanProcessMapperImpl implements LoanProcessMapper {

    @Override
    public LoanProcessDTO toDto(LoanProcess arg0) {
        if ( arg0 == null ) {
            return null;
        }

        LoanProcessDTO loanProcessDTO = new LoanProcessDTO();

        loanProcessDTO.setBankingAccountEntityMnemonic( arg0.getBankingAccountEntityMnemonic() );
        loanProcessDTO.setBankingAccountNumber( arg0.getBankingAccountNumber() );
        loanProcessDTO.setBankingAccountType( arg0.getBankingAccountType() );
        loanProcessDTO.setBankingEntityMnemonic( arg0.getBankingEntityMnemonic() );
        loanProcessDTO.setBankingProductMnemonic( arg0.getBankingProductMnemonic() );
        loanProcessDTO.setDebtorCountry( arg0.getDebtorCountry() );
        loanProcessDTO.setDebtorIdentification( arg0.getDebtorIdentification() );
        loanProcessDTO.setDebtorIdentificationType( arg0.getDebtorIdentificationType() );
        loanProcessDTO.setEndDate( arg0.getEndDate() );
        loanProcessDTO.setGivenAmount( arg0.getGivenAmount() );
        loanProcessDTO.setId( arg0.getId() );
        loanProcessDTO.setJustification( arg0.getJustification() );
        loanProcessDTO.setLoanPeriod( arg0.getLoanPeriod() );
        loanProcessDTO.setLoanProcessStatus( arg0.getLoanProcessStatus() );
        loanProcessDTO.setName( arg0.getName() );
        loanProcessDTO.setRequestedAmount( arg0.getRequestedAmount() );
        loanProcessDTO.setRulesEngineResult( arg0.isRulesEngineResult() );
        loanProcessDTO.setStartDate( arg0.getStartDate() );

        return loanProcessDTO;
    }

    @Override
    public List<LoanProcessDTO> toDto(List<LoanProcess> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<LoanProcessDTO> list = new ArrayList<LoanProcessDTO>( arg0.size() );
        for ( LoanProcess loanProcess : arg0 ) {
            list.add( toDto( loanProcess ) );
        }

        return list;
    }

    @Override
    public List<LoanProcess> toEntity(List<LoanProcessDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<LoanProcess> list = new ArrayList<LoanProcess>( arg0.size() );
        for ( LoanProcessDTO loanProcessDTO : arg0 ) {
            list.add( toEntity( loanProcessDTO ) );
        }

        return list;
    }

    @Override
    public LoanProcess toEntity(LoanProcessDTO loanProcessDTO) {
        if ( loanProcessDTO == null ) {
            return null;
        }

        LoanProcess loanProcess = new LoanProcess();

        loanProcess.setBankingAccountEntityMnemonic( loanProcessDTO.getBankingAccountEntityMnemonic() );
        loanProcess.setBankingAccountNumber( loanProcessDTO.getBankingAccountNumber() );
        loanProcess.setBankingAccountType( loanProcessDTO.getBankingAccountType() );
        loanProcess.setBankingEntityMnemonic( loanProcessDTO.getBankingEntityMnemonic() );
        loanProcess.setBankingProductMnemonic( loanProcessDTO.getBankingProductMnemonic() );
        loanProcess.setDebtorCountry( loanProcessDTO.getDebtorCountry() );
        loanProcess.setDebtorIdentification( loanProcessDTO.getDebtorIdentification() );
        loanProcess.setDebtorIdentificationType( loanProcessDTO.getDebtorIdentificationType() );
        loanProcess.setEndDate( loanProcessDTO.getEndDate() );
        loanProcess.setGivenAmount( loanProcessDTO.getGivenAmount() );
        loanProcess.setJustification( loanProcessDTO.getJustification() );
        loanProcess.setLoanPeriod( loanProcessDTO.getLoanPeriod() );
        loanProcess.setLoanProcessStatus( loanProcessDTO.getLoanProcessStatus() );
        loanProcess.setName( loanProcessDTO.getName() );
        loanProcess.setRequestedAmount( loanProcessDTO.getRequestedAmount() );
        loanProcess.setRulesEngineResult( loanProcessDTO.isRulesEngineResult() );
        loanProcess.setId( loanProcessDTO.getId() );
        loanProcess.startDate( loanProcessDTO.getStartDate() );

        return loanProcess;
    }
}
