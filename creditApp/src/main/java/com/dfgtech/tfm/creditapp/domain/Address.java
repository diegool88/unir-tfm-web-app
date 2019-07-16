package com.dfgtech.tfm.creditapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.dfgtech.tfm.creditapp.domain.enumeration.AddressType;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 40)
    @Pattern(regexp = "[A-Za-z0-9\\s]+")
    @Column(name = "main_street", length = 40, nullable = false)
    private String mainStreet;

    @Size(min = 5, max = 40)
    @Pattern(regexp = "[A-Za-z0-9\\s]+")
    @Column(name = "secondary_street", length = 40)
    private String secondaryStreet;

    @NotNull
    @Size(min = 1, max = 15)
    @Pattern(regexp = "[A-Za-z0-9]+")
    @Column(name = "number", length = 15, nullable = false)
    private String number;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "city", length = 30, nullable = false)
    private String city;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "province", length = 30, nullable = false)
    private String province;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "country", length = 30, nullable = false)
    private String country;

    @NotNull
    @Column(name = "postal_code", nullable = false)
    private Integer postalCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private AddressType addressType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("addresses")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainStreet() {
        return mainStreet;
    }

    public Address mainStreet(String mainStreet) {
        this.mainStreet = mainStreet;
        return this;
    }

    public void setMainStreet(String mainStreet) {
        this.mainStreet = mainStreet;
    }

    public String getSecondaryStreet() {
        return secondaryStreet;
    }

    public Address secondaryStreet(String secondaryStreet) {
        this.secondaryStreet = secondaryStreet;
        return this;
    }

    public void setSecondaryStreet(String secondaryStreet) {
        this.secondaryStreet = secondaryStreet;
    }

    public String getNumber() {
        return number;
    }

    public Address number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public Address province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public Address country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public Address postalCode(Integer postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public Address addressType(AddressType addressType) {
        this.addressType = addressType;
        return this;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Address customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", mainStreet='" + getMainStreet() + "'" +
            ", secondaryStreet='" + getSecondaryStreet() + "'" +
            ", number='" + getNumber() + "'" +
            ", city='" + getCity() + "'" +
            ", province='" + getProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", postalCode=" + getPostalCode() +
            ", addressType='" + getAddressType() + "'" +
            "}";
    }
}
