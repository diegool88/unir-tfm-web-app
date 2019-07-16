package com.dfgtech.tfm.creditapp.service.dto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.dfgtech.tfm.creditapp.domain.enumeration.Genre;
import com.dfgtech.tfm.creditapp.domain.enumeration.Relation;

/**
 * A DTO for the {@link com.dfgtech.tfm.creditapp.domain.PersonalReference} entity.
 */
public class PersonalReferenceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String name;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String lastname;

    @NotNull
    private Genre genre;

    @NotNull
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}")
    private String email;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private Relation relation;

    /**
     * Relaciones Microservicio administracion clientes
     */
    @ApiModelProperty(value = "Relaciones Microservicio administracion clientes")

    private Long customerId;

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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonalReferenceDTO personalReferenceDTO = (PersonalReferenceDTO) o;
        if (personalReferenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personalReferenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonalReferenceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", genre='" + getGenre() + "'" +
            ", email='" + getEmail() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", relation='" + getRelation() + "'" +
            ", customer=" + getCustomerId() +
            "}";
    }
}
