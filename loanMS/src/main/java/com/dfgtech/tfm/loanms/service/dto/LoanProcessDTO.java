package com.dfgtech.tfm.loanms.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.dfgtech.tfm.loanms.domain.enumeration.LoanProcessStatus;

/**
 * A DTO for the {@link com.dfgtech.tfm.loanms.domain.LoanProcess} entity.
 */
public class LoanProcessDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z0-9\\s]+")
    private String name;

    @NotNull
    private Double requestedAmount;

    private Double givenAmount;

    @NotNull
    private Integer loanPeriod;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Size(min = 1, max = 255)
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String justification;

    @NotNull
    private String debtorIdentification;

    @NotNull
    private String debtorIdentificationType;

    @NotNull
    private String debtorCountry;

    @NotNull
    private String bankingEntityMnemonic;

    @NotNull
    private String bankingProductMnemonic;

    private LoanProcessStatus loanProcessStatus;


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

    public Double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Double getGivenAmount() {
        return givenAmount;
    }

    public void setGivenAmount(Double givenAmount) {
        this.givenAmount = givenAmount;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
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

    public String getBankingEntityMnemonic() {
        return bankingEntityMnemonic;
    }

    public void setBankingEntityMnemonic(String bankingEntityMnemonic) {
        this.bankingEntityMnemonic = bankingEntityMnemonic;
    }

    public String getBankingProductMnemonic() {
        return bankingProductMnemonic;
    }

    public void setBankingProductMnemonic(String bankingProductMnemonic) {
        this.bankingProductMnemonic = bankingProductMnemonic;
    }

    public LoanProcessStatus getLoanProcessStatus() {
        return loanProcessStatus;
    }

    public void setLoanProcessStatus(LoanProcessStatus loanProcessStatus) {
        this.loanProcessStatus = loanProcessStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoanProcessDTO loanProcessDTO = (LoanProcessDTO) o;
        if (loanProcessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loanProcessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LoanProcessDTO{" +
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
            ", loanProcessStatus='" + getLoanProcessStatus() + "'" +
            "}";
    }
}
