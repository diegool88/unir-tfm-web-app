package com.dfgtech.tfm.creditapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.dfgtech.tfm.creditapp.domain.enumeration.Genre;

import com.dfgtech.tfm.creditapp.domain.enumeration.Relation;

/**
 * A PersonalReference.
 */
@Entity
@Table(name = "personal_reference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonalReference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "lastname", length = 30, nullable = false)
    private String lastname;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    @NotNull
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "relation", nullable = false)
    private Relation relation;

    /**
     * Relaciones Microservicio administracion clientes
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("personalReferences")
    private Customer customer;

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

    public PersonalReference name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public PersonalReference lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Genre getGenre() {
        return genre;
    }

    public PersonalReference genre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getEmail() {
        return email;
    }

    public PersonalReference email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public PersonalReference birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Relation getRelation() {
        return relation;
    }

    public PersonalReference relation(Relation relation) {
        this.relation = relation;
        return this;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public Customer getCustomer() {
        return customer;
    }

    public PersonalReference customer(Customer customer) {
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
        if (!(o instanceof PersonalReference)) {
            return false;
        }
        return id != null && id.equals(((PersonalReference) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PersonalReference{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", genre='" + getGenre() + "'" +
            ", email='" + getEmail() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", relation='" + getRelation() + "'" +
            "}";
    }
}
