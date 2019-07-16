package com.dfgtech.tfm.regionms.service.mapper;

import com.dfgtech.tfm.regionms.domain.*;
import com.dfgtech.tfm.regionms.service.dto.CityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring", uses = {StateProvinceMapper.class})
public interface CityMapper extends EntityMapper<CityDTO, City> {

    @Mapping(source = "state.id", target = "stateId")
    CityDTO toDto(City city);

    @Mapping(source = "stateId", target = "state")
    City toEntity(CityDTO cityDTO);

    default City fromId(Long id) {
        if (id == null) {
            return null;
        }
        City city = new City();
        city.setId(id);
        return city;
    }
}
