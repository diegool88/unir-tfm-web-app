package com.dfgtech.tfm.regionms.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A StateProvince.
 */
@Entity
@Table(name = "state_province")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StateProvince implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    /**
     * Relaciones Microservicio administracion clientes
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("stateProvinces")
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public StateProvince name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public StateProvince country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StateProvince)) {
            return false;
        }
        return id != null && id.equals(((StateProvince) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StateProvince{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
