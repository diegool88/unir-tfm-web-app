package com.dfgtech.tfm.loanms.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.dfgtech.tfm.loanms.domain.Warranty} entity.
 */
@ApiModel(description = "Entidades")
public class WarrantyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String name;

    private String description;

    @NotNull
    private Double value;

    @Lob
    private byte[] document;

    private String documentContentType;
    @NotNull
    private String debtorIdentification;

    @NotNull
    private String debtorIdentificationType;

    @NotNull
    private String debtorCountry;

    /**
     * Relaciones Microservicio de credito
     */
    @ApiModelProperty(value = "Relaciones Microservicio de credito")

    private Set<LoanProcessDTO> loanProcesses = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocumentContentType() {
        return documentContentType;
    }

    public void setDocumentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
    }

    public String getDebtorIdentification() {
        return debtorIdentification;
    }

    public void setDebtorIdentification(String debtorIdentification) {
        this.debtorIdentification = debtorIdentification;
    }

    public String getDebtorIdentificationType() {
        return debtorIdentificationType;
    }

    public void setDebtorIdentificationType(String debtorIdentificationType) {
        this.debtorIdentificationType = debtorIdentificationType;
    }

    public String getDebtorCountry() {
        return debtorCountry;
    }

    public void setDebtorCountry(String debtorCountry) {
        this.debtorCountry = debtorCountry;
    }

    public Set<LoanProcessDTO> getLoanProcesses() {
        return loanProcesses;
    }

    public void setLoanProcesses(Set<LoanProcessDTO> loanProcesses) {
        this.loanProcesses = loanProcesses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WarrantyDTO warrantyDTO = (WarrantyDTO) o;
        if (warrantyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), warrantyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WarrantyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", value=" + getValue() +
            ", document='" + getDocument() + "'" +
            ", debtorIdentification='" + getDebtorIdentification() + "'" +
            ", debtorIdentificationType='" + getDebtorIdentificationType() + "'" +
            ", debtorCountry='" + getDebtorCountry() + "'" +
            "}";
    }
}
