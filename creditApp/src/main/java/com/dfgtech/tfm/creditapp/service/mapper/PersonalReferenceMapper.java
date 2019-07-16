package com.dfgtech.tfm.creditapp.service.mapper;

import com.dfgtech.tfm.creditapp.domain.*;
import com.dfgtech.tfm.creditapp.service.dto.PersonalReferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalReference} and its DTO {@link PersonalReferenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface PersonalReferenceMapper extends EntityMapper<PersonalReferenceDTO, PersonalReference> {

    @Mapping(source = "customer.id", target = "customerId")
    PersonalReferenceDTO toDto(PersonalReference personalReference);

    @Mapping(source = "customerId", target = "customer")
    PersonalReference toEntity(PersonalReferenceDTO personalReferenceDTO);

    default PersonalReference fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonalReference personalReference = new PersonalReference();
        personalReference.setId(id);
        return personalReference;
    }
}
