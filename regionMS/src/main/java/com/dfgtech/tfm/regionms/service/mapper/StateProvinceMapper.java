package com.dfgtech.tfm.regionms.service.mapper;

import com.dfgtech.tfm.regionms.domain.*;
import com.dfgtech.tfm.regionms.service.dto.StateProvinceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StateProvince} and its DTO {@link StateProvinceDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class})
public interface StateProvinceMapper extends EntityMapper<StateProvinceDTO, StateProvince> {

    @Mapping(source = "country.id", target = "countryId")
    StateProvinceDTO toDto(StateProvince stateProvince);

    @Mapping(source = "countryId", target = "country")
    StateProvince toEntity(StateProvinceDTO stateProvinceDTO);

    default StateProvince fromId(Long id) {
        if (id == null) {
            return null;
        }
        StateProvince stateProvince = new StateProvince();
        stateProvince.setId(id);
        return stateProvince;
    }
}
