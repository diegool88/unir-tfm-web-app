package com.dfgtech.tfm.regionms.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dfgtech.tfm.regionms.domain.StateProvince} entity.
 */
public class StateProvinceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String name;

    /**
     * Relaciones Microservicio administracion clientes
     */
    @ApiModelProperty(value = "Relaciones Microservicio administracion clientes")

    private Long countryId;

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

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StateProvinceDTO stateProvinceDTO = (StateProvinceDTO) o;
        if (stateProvinceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stateProvinceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StateProvinceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", country=" + getCountryId() +
            "}";
    }
}
