package com.dfgtech.tfm.loanms.service.mapper;

import com.dfgtech.tfm.loanms.domain.LoanProcess;
import com.dfgtech.tfm.loanms.domain.Warranty;
import com.dfgtech.tfm.loanms.service.dto.LoanProcessDTO;
import com.dfgtech.tfm.loanms.service.dto.WarrantyDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-08-25T20:50:48-0500",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.17.0.v20190306-2240, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class WarrantyMapperImpl implements WarrantyMapper {

    @Autowired
    private LoanProcessMapper loanProcessMapper;

    @Override
    public WarrantyDTO toDto(Warranty arg0) {
        if ( arg0 == null ) {
            return null;
        }

        WarrantyDTO warrantyDTO = new WarrantyDTO();

        warrantyDTO.setId( arg0.getId() );
        warrantyDTO.setName( arg0.getName() );
        warrantyDTO.setDescription( arg0.getDescription() );
        warrantyDTO.setValue( arg0.getValue() );
        byte[] document = arg0.getDocument();
        if ( document != null ) {
            warrantyDTO.setDocument( Arrays.copyOf( document, document.length ) );
        }
        warrantyDTO.setDocumentContentType( arg0.getDocumentContentType() );
        warrantyDTO.setDebtorIdentification( arg0.getDebtorIdentification() );
        warrantyDTO.setDebtorIdentificationType( arg0.getDebtorIdentificationType() );
        warrantyDTO.setDebtorCountry( arg0.getDebtorCountry() );
        warrantyDTO.setLoanProcesses( loanProcessSetToLoanProcessDTOSet( arg0.getLoanProcesses() ) );

        return warrantyDTO;
    }

    @Override
    public List<WarrantyDTO> toDto(List<Warranty> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<WarrantyDTO> list = new ArrayList<WarrantyDTO>( arg0.size() );
        for ( Warranty warranty : arg0 ) {
            list.add( toDto( warranty ) );
        }

        return list;
    }

    @Override
    public Warranty toEntity(WarrantyDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Warranty warranty = new Warranty();

        warranty.setDebtorCountry( arg0.getDebtorCountry() );
        warranty.setDebtorIdentification( arg0.getDebtorIdentification() );
        warranty.setDebtorIdentificationType( arg0.getDebtorIdentificationType() );
        warranty.setDescription( arg0.getDescription() );
        byte[] document = arg0.getDocument();
        if ( document != null ) {
            warranty.setDocument( Arrays.copyOf( document, document.length ) );
        }
        warranty.setDocumentContentType( arg0.getDocumentContentType() );
        warranty.setLoanProcesses( loanProcessDTOSetToLoanProcessSet( arg0.getLoanProcesses() ) );
        warranty.setName( arg0.getName() );
        warranty.setId( arg0.getId() );
        warranty.value( arg0.getValue() );

        return warranty;
    }

    @Override
    public List<Warranty> toEntity(List<WarrantyDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Warranty> list = new ArrayList<Warranty>( arg0.size() );
        for ( WarrantyDTO warrantyDTO : arg0 ) {
            list.add( toEntity( warrantyDTO ) );
        }

        return list;
    }

    protected Set<LoanProcessDTO> loanProcessSetToLoanProcessDTOSet(Set<LoanProcess> set) {
        if ( set == null ) {
            return null;
        }

        Set<LoanProcessDTO> set1 = new HashSet<LoanProcessDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( LoanProcess loanProcess : set ) {
            set1.add( loanProcessMapper.toDto( loanProcess ) );
        }

        return set1;
    }

    protected Set<LoanProcess> loanProcessDTOSetToLoanProcessSet(Set<LoanProcessDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<LoanProcess> set1 = new HashSet<LoanProcess>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( LoanProcessDTO loanProcessDTO : set ) {
            set1.add( loanProcessMapper.toEntity( loanProcessDTO ) );
        }

        return set1;
    }
}
