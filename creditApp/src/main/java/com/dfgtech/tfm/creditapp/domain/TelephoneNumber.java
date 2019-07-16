package com.dfgtech.tfm.creditapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TelephoneNumber.
 */
@Entity
@Table(name = "telephone_number")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TelephoneNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "country_code", nullable = false)
    private Integer countryCode;

    @NotNull
    @Column(name = "region_code", nullable = false)
    private Integer regionCode;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("telephoneNumbers")
    private Address address;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public TelephoneNumber countryCode(Integer countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getRegionCode() {
        return regionCode;
    }

    public TelephoneNumber regionCode(Integer regionCode) {
        this.regionCode = regionCode;
        return this;
    }

    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }

    public Integer getNumber() {
        return number;
    }

    public TelephoneNumber number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Address getAddress() {
        return address;
    }

    public TelephoneNumber address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelephoneNumber)) {
            return false;
        }
        return id != null && id.equals(((TelephoneNumber) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TelephoneNumber{" +
            "id=" + getId() +
            ", countryCode=" + getCountryCode() +
            ", regionCode=" + getRegionCode() +
            ", number=" + getNumber() +
            "}";
    }
}
