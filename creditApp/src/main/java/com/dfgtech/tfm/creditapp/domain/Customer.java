package com.dfgtech.tfm.creditapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.dfgtech.tfm.creditapp.domain.enumeration.IdentificationType;

import com.dfgtech.tfm.creditapp.domain.enumeration.Genre;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "firstname", length = 30, nullable = false)
    private String firstname;

    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "second_name", length = 30)
    private String secondName;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "lastname", length = 30, nullable = false)
    private String lastname;

    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "second_lastname", length = 30)
    private String secondLastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "identification_type")
    private IdentificationType identificationType;

    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = "[A-Z0-9]+")
    @Column(name = "identification_number", length = 20, nullable = false)
    private String identificationNumber;

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
    @Size(min = 1, max = 20)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "country", length = 20, nullable = false)
    private String country;

    @NotNull
    @Column(name = "client_since", nullable = false)
    private LocalDate clientSince;

    @NotNull
    @Column(name = "monthly_income", nullable = false)
    private Double monthlyIncome;

    /**
     * Relaciones Microservicio administracion clientes
     */
    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public Customer firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondName() {
        return secondName;
    }

    public Customer secondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastname() {
        return lastname;
    }

    public Customer lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSecondLastname() {
        return secondLastname;
    }

    public Customer secondLastname(String secondLastname) {
        this.secondLastname = secondLastname;
        return this;
    }

    public void setSecondLastname(String secondLastname) {
        this.secondLastname = secondLastname;
    }

    public IdentificationType getIdentificationType() {
        return identificationType;
    }

    public Customer identificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
        return this;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public Customer identificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
        return this;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Genre getGenre() {
        return genre;
    }

    public Customer genre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Customer birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public Customer country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getClientSince() {
        return clientSince;
    }

    public Customer clientSince(LocalDate clientSince) {
        this.clientSince = clientSince;
        return this;
    }

    public void setClientSince(LocalDate clientSince) {
        this.clientSince = clientSince;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public Customer monthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
        return this;
    }

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public User getUser() {
        return user;
    }

    public Customer user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", firstname='" + getFirstname() + "'" +
            ", secondName='" + getSecondName() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", secondLastname='" + getSecondLastname() + "'" +
            ", identificationType='" + getIdentificationType() + "'" +
            ", identificationNumber='" + getIdentificationNumber() + "'" +
            ", genre='" + getGenre() + "'" +
            ", email='" + getEmail() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", country='" + getCountry() + "'" +
            ", clientSince='" + getClientSince() + "'" +
            ", monthlyIncome=" + getMonthlyIncome() +
            "}";
    }
}
