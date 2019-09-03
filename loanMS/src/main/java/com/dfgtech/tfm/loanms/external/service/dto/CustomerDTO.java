package com.dfgtech.tfm.loanms.external.service.dto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dfgtech.tfm.creditapp.domain.Customer} entity.
 */
public class CustomerDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String firstname;

    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String secondName;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String lastname;

    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String secondLastname;

    private IdentificationType identificationType;

    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = "[A-Z0-9]+")
    private String identificationNumber;

    @NotNull
    private Genre genre;

    @NotNull
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}")
    private String email;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String country;

    @NotNull
    private LocalDate clientSince;

    @NotNull
    private Double monthlyIncome;

    /**
     * Relaciones Microservicio administracion clientes
     */
    @ApiModelProperty(value = "Relaciones Microservicio administracion clientes")

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSecondLastname() {
        return secondLastname;
    }

    public void setSecondLastname(String secondLastname) {
        this.secondLastname = secondLastname;
    }

    public IdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getClientSince() {
        return clientSince;
    }

    public void setClientSince(LocalDate clientSince) {
        this.clientSince = clientSince;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;
        if (customerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
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
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
