package com.dfgtech.tfm.creditapp.service.mapper;

import com.dfgtech.tfm.creditapp.domain.*;
import com.dfgtech.tfm.creditapp.service.dto.TelephoneNumberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TelephoneNumber} and its DTO {@link TelephoneNumberDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface TelephoneNumberMapper extends EntityMapper<TelephoneNumberDTO, TelephoneNumber> {

    @Mapping(source = "address.id", target = "addressId")
    TelephoneNumberDTO toDto(TelephoneNumber telephoneNumber);

    @Mapping(source = "addressId", target = "address")
    TelephoneNumber toEntity(TelephoneNumberDTO telephoneNumberDTO);

    default TelephoneNumber fromId(Long id) {
        if (id == null) {
            return null;
        }
        TelephoneNumber telephoneNumber = new TelephoneNumber();
        telephoneNumber.setId(id);
        return telephoneNumber;
    }
}
