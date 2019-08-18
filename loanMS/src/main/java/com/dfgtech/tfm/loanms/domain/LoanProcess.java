package com.dfgtech.tfm.loanms.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.dfgtech.tfm.loanms.domain.enumeration.LoanProcessStatus;

/**
 * A LoanProcess.
 */
@Entity
@Table(name = "loan_process")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LoanProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z0-9\\s]+")
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @NotNull
    @Column(name = "requested_amount", nullable = false)
    private Double requestedAmount;

    @Column(name = "given_amount")
    private Double givenAmount;

    @NotNull
    @Column(name = "loan_period", nullable = false)
    private Integer loanPeriod;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Size(min = 1, max = 255)
    @Pattern(regexp = "[A-Za-z\\s]+")
    @Column(name = "justification", length = 255, nullable = false)
    private String justification;

    @NotNull
    @Column(name = "debtor_identification", nullable = false)
    private String debtorIdentification;

    @NotNull
    @Column(name = "debtor_identification_type", nullable = false)
    private String debtorIdentificationType;

    @NotNull
    @Column(name = "debtor_country", nullable = false)
    private String debtorCountry;

    @NotNull
    @Column(name = "banking_entity_mnemonic", nullable = false)
    private String bankingEntityMnemonic;

    @NotNull
    @Column(name = "banking_product_mnemonic", nullable = false)
    private String bankingProductMnemonic;

    @Column(name = "rules_engine_result")
    private Boolean rulesEngineResult;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_process_status")
    private LoanProcessStatus loanProcessStatus;

    @ManyToMany(mappedBy = "loanProcesses")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Warranty> warranties = new HashSet<>();

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

    public LoanProcess name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRequestedAmount() {
        return requestedAmount;
    }

    public LoanProcess requestedAmount(Double requestedAmount) {
        this.requestedAmount = requestedAmount;
        return this;
    }

    public void setRequestedAmount(Double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Double getGivenAmount() {
        return givenAmount;
    }

    public LoanProcess givenAmount(Double givenAmount) {
        this.givenAmount = givenAmount;
        return this;
    }

    public void setGivenAmount(Double givenAmount) {
        this.givenAmount = givenAmount;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public LoanProcess loanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
        return this;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LoanProcess startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LoanProcess endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getJustification() {
        return justification;
    }

    public LoanProcess justification(String justification) {
        this.justification = justification;
        return this;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getDebtorIdentification() {
        return debtorIdentification;
    }

    public LoanProcess debtorIdentification(String debtorIdentification) {
        this.debtorIdentification = debtorIdentification;
        return this;
    }

    public void setDebtorIdentification(String debtorIdentification) {
        this.debtorIdentification = debtorIdentification;
    }

    public String getDebtorIdentificationType() {
        return debtorIdentificationType;
    }

    public LoanProcess debtorIdentificationType(String debtorIdentificationType) {
        this.debtorIdentificationType = debtorIdentificationType;
        return this;
    }

    public void setDebtorIdentificationType(String debtorIdentificationType) {
        this.debtorIdentificationType = debtorIdentificationType;
    }

    public String getDebtorCountry() {
        return debtorCountry;
    }

    public LoanProcess debtorCountry(String debtorCountry) {
        this.debtorCountry = debtorCountry;
        return this;
    }

    public void setDebtorCountry(String debtorCountry) {
        this.debtorCountry = debtorCountry;
    }

    public String getBankingEntityMnemonic() {
        return bankingEntityMnemonic;
    }

    public LoanProcess bankingEntityMnemonic(String bankingEntityMnemonic) {
        this.bankingEntityMnemonic = bankingEntityMnemonic;
        return this;
    }

    public void setBankingEntityMnemonic(String bankingEntityMnemonic) {
        this.bankingEntityMnemonic = bankingEntityMnemonic;
    }

    public String getBankingProductMnemonic() {
        return bankingProductMnemonic;
    }

    public LoanProcess bankingProductMnemonic(String bankingProductMnemonic) {
        this.bankingProductMnemonic = bankingProductMnemonic;
        return this;
    }

    public void setBankingProductMnemonic(String bankingProductMnemonic) {
        this.bankingProductMnemonic = bankingProductMnemonic;
    }

    public Boolean isRulesEngineResult() {
        return rulesEngineResult;
    }

    public LoanProcess rulesEngineResult(Boolean rulesEngineResult) {
        this.rulesEngineResult = rulesEngineResult;
        return this;
    }

    public void setRulesEngineResult(Boolean rulesEngineResult) {
        this.rulesEngineResult = rulesEngineResult;
    }

    public LoanProcessStatus getLoanProcessStatus() {
        return loanProcessStatus;
    }

    public LoanProcess loanProcessStatus(LoanProcessStatus loanProcessStatus) {
        this.loanProcessStatus = loanProcessStatus;
        return this;
    }

    public void setLoanProcessStatus(LoanProcessStatus loanProcessStatus) {
        this.loanProcessStatus = loanProcessStatus;
    }

    public Set<Warranty> getWarranties() {
        return warranties;
    }

    public LoanProcess warranties(Set<Warranty> warranties) {
        this.warranties = warranties;
        return this;
    }

    public LoanProcess addWarranties(Warranty warranty) {
        this.warranties.add(warranty);
        warranty.getLoanProcesses().add(this);
        return this;
    }

    public LoanProcess removeWarranties(Warranty warranty) {
        this.warranties.remove(warranty);
        warranty.getLoanProcesses().remove(this);
        return this;
    }

    public void setWarranties(Set<Warranty> warranties) {
        this.warranties = warranties;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanProcess)) {
            return false;
        }
        return id != null && id.equals(((LoanProcess) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LoanProcess{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", requestedAmount=" + getRequestedAmount() +
            ", givenAmount=" + getGivenAmount() +
            ", loanPeriod=" + getLoanPeriod() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", justification='" + getJustification() + "'" +
            ", debtorIdentification='" + getDebtorIdentification() + "'" +
            ", debtorIdentificationType='" + getDebtorIdentificationType() + "'" +
            ", debtorCountry='" + getDebtorCountry() + "'" +
            ", bankingEntityMnemonic='" + getBankingEntityMnemonic() + "'" +
            ", bankingProductMnemonic='" + getBankingProductMnemonic() + "'" +
            ", rulesEngineResult='" + isRulesEngineResult() + "'" +
            ", loanProcessStatus='" + getLoanProcessStatus() + "'" +
            "}";
    }
}
