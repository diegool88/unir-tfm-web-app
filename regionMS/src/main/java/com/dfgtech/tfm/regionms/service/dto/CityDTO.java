package com.dfgtech.tfm.regionms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dfgtech.tfm.regionms.domain.City} entity.
 */
public class CityDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String name;


    private Long stateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateProvinceId) {
        this.stateId = stateProvinceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CityDTO cityDTO = (CityDTO) o;
        if (cityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", state=" + getStateId() +
            "}";
    }
}
