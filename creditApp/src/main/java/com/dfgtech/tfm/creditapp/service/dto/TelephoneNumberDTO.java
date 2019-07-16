package com.dfgtech.tfm.creditapp.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dfgtech.tfm.creditapp.domain.TelephoneNumber} entity.
 */
public class TelephoneNumberDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer countryCode;

    @NotNull
    private Integer regionCode;

    @NotNull
    private Integer number;


    private Long addressId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TelephoneNumberDTO telephoneNumberDTO = (TelephoneNumberDTO) o;
        if (telephoneNumberDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), telephoneNumberDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TelephoneNumberDTO{" +
            "id=" + getId() +
            ", countryCode=" + getCountryCode() +
            ", regionCode=" + getRegionCode() +
            ", number=" + getNumber() +
            ", address=" + getAddressId() +
            "}";
    }
}
