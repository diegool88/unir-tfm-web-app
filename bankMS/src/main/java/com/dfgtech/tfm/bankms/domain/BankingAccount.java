package com.dfgtech.tfm.bankms.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.dfgtech.tfm.bankms.domain.enumeration.AccountType;

/**
 * A BankingAccount.
 */
@Entity
@Table(name = "banking_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BankingAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @NotNull
    @Column(name = "current_balance", nullable = false)
    private Double currentBalance;

    @NotNull
    @Column(name = "available_balance", nullable = false)
    private Double availableBalance;

    @NotNull
    @Column(name = "customer_identification", nullable = false)
    private String customerIdentification;

    @NotNull
    @Column(name = "customer_identification_type", nullable = false)
    private String customerIdentificationType;

    @NotNull
    @Column(name = "customer_country", nullable = false)
    private String customerCountry;

    @NotNull
    @Size(max = 10)
    @Pattern(regexp = "[A-Z0-9]+")
    @Column(name = "banking_entity_mnemonic", length = 10, nullable = false)
    private String bankingEntityMnemonic;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public BankingAccount number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BankingAccount accountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public BankingAccount currentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
        return this;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public BankingAccount availableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
        return this;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getCustomerIdentification() {
        return customerIdentification;
    }

    public BankingAccount customerIdentification(String customerIdentification) {
        this.customerIdentification = customerIdentification;
        return this;
    }

    public void setCustomerIdentification(String customerIdentification) {
        this.customerIdentification = customerIdentification;
    }

    public String getCustomerIdentificationType() {
        return customerIdentificationType;
    }

    public BankingAccount customerIdentificationType(String customerIdentificationType) {
        this.customerIdentificationType = customerIdentificationType;
        return this;
    }

    public void setCustomerIdentificationType(String customerIdentificationType) {
        this.customerIdentificationType = customerIdentificationType;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public BankingAccount customerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
        return this;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getBankingEntityMnemonic() {
        return bankingEntityMnemonic;
    }

    public BankingAccount bankingEntityMnemonic(String bankingEntityMnemonic) {
        this.bankingEntityMnemonic = bankingEntityMnemonic;
        return this;
    }

    public void setBankingEntityMnemonic(String bankingEntityMnemonic) {
        this.bankingEntityMnemonic = bankingEntityMnemonic;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankingAccount)) {
            return false;
        }
        return id != null && id.equals(((BankingAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BankingAccount{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", accountType='" + getAccountType() + "'" +
            ", currentBalance=" + getCurrentBalance() +
            ", availableBalance=" + getAvailableBalance() +
            ", customerIdentification='" + getCustomerIdentification() + "'" +
            ", customerIdentificationType='" + getCustomerIdentificationType() + "'" +
            ", customerCountry='" + getCustomerCountry() + "'" +
            ", bankingEntityMnemonic='" + getBankingEntityMnemonic() + "'" +
            "}";
    }
}
