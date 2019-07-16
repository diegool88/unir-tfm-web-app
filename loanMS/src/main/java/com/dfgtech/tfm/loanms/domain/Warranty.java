package com.dfgtech.tfm.loanms.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidades
 */
@Entity
@Table(name = "warranty")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Warranty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "value", nullable = false)
    private Double value;

    @Lob
    @Column(name = "document")
    private byte[] document;

    @Column(name = "document_content_type")
    private String documentContentType;

    @NotNull
    @Column(name = "debtor_identification", nullable = false)
    private String debtorIdentification;

    @NotNull
    @Column(name = "debtor_identification_type", nullable = false)
    private String debtorIdentificationType;

    @NotNull
    @Column(name = "debtor_country", nullable = false)
    private String debtorCountry;

    /**
     * Relaciones Microservicio de credito
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "warranty_loan_process",
               joinColumns = @JoinColumn(name = "warranty_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "loan_process_id", referencedColumnName = "id"))
    private Set<LoanProcess> loanProcesses = new HashSet<>();

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

    public Warranty name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Warranty description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public Warranty value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public byte[] getDocument() {
        return document;
    }

    public Warranty document(byte[] document) {
        this.document = document;
        return this;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocumentContentType() {
        return documentContentType;
    }

    public Warranty documentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
        return this;
    }

    public void setDocumentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
    }

    public String getDebtorIdentification() {
        return debtorIdentification;
    }

    public Warranty debtorIdentification(String debtorIdentification) {
        this.debtorIdentification = debtorIdentification;
        return this;
    }

    public void setDebtorIdentification(String debtorIdentification) {
        this.debtorIdentification = debtorIdentification;
    }

    public String getDebtorIdentificationType() {
        return debtorIdentificationType;
    }

    public Warranty debtorIdentificationType(String debtorIdentificationType) {
        this.debtorIdentificationType = debtorIdentificationType;
        return this;
    }

    public void setDebtorIdentificationType(String debtorIdentificationType) {
        this.debtorIdentificationType = debtorIdentificationType;
    }

    public String getDebtorCountry() {
        return debtorCountry;
    }

    public Warranty debtorCountry(String debtorCountry) {
        this.debtorCountry = debtorCountry;
        return this;
    }

    public void setDebtorCountry(String debtorCountry) {
        this.debtorCountry = debtorCountry;
    }

    public Set<LoanProcess> getLoanProcesses() {
        return loanProcesses;
    }

    public Warranty loanProcesses(Set<LoanProcess> loanProcesses) {
        this.loanProcesses = loanProcesses;
        return this;
    }

    public Warranty addLoanProcess(LoanProcess loanProcess) {
        this.loanProcesses.add(loanProcess);
        loanProcess.getWarranties().add(this);
        return this;
    }

    public Warranty removeLoanProcess(LoanProcess loanProcess) {
        this.loanProcesses.remove(loanProcess);
        loanProcess.getWarranties().remove(this);
        return this;
    }

    public void setLoanProcesses(Set<LoanProcess> loanProcesses) {
        this.loanProcesses = loanProcesses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Warranty)) {
            return false;
        }
        return id != null && id.equals(((Warranty) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Warranty{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", value=" + getValue() +
            ", document='" + getDocument() + "'" +
            ", documentContentType='" + getDocumentContentType() + "'" +
            ", debtorIdentification='" + getDebtorIdentification() + "'" +
            ", debtorIdentificationType='" + getDebtorIdentificationType() + "'" +
            ", debtorCountry='" + getDebtorCountry() + "'" +
            "}";
    }
}
