package com.dfgtech.tfm.bankms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.dfgtech.tfm.bankms.domain.enumeration.AccountType;

/**
 * A DTO for the {@link com.dfgtech.tfm.bankms.domain.BankingAccount} entity.
 */
public class BankingAccountDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer number;

    @NotNull
    private AccountType accountType;

    @NotNull
    private Double currentBalance;

    @NotNull
    private Double availableBalance;

    @NotNull
    private String customerIdentification;

    @NotNull
    private String customerIdentificationType;

    @NotNull
    private String customerCountry;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getCustomerIdentification() {
        return customerIdentification;
    }

    public void setCustomerIdentification(String customerIdentification) {
        this.customerIdentification = customerIdentification;
    }

    public String getCustomerIdentificationType() {
        return customerIdentificationType;
    }

    public void setCustomerIdentificationType(String customerIdentificationType) {
        this.customerIdentificationType = customerIdentificationType;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BankingAccountDTO bankingAccountDTO = (BankingAccountDTO) o;
        if (bankingAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankingAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankingAccountDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", accountType='" + getAccountType() + "'" +
            ", currentBalance=" + getCurrentBalance() +
            ", availableBalance=" + getAvailableBalance() +
            ", customerIdentification='" + getCustomerIdentification() + "'" +
            ", customerIdentificationType='" + getCustomerIdentificationType() + "'" +
            ", customerCountry='" + getCustomerCountry() + "'" +
            "}";
    }
}
