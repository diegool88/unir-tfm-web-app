package com.dfgtech.tfm.bankms.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.dfgtech.tfm.bankms.domain.enumeration.TransactionType;
import com.dfgtech.tfm.bankms.domain.enumeration.AccountType;
import com.dfgtech.tfm.bankms.domain.enumeration.AccountType;

/**
 * A DTO for the {@link com.dfgtech.tfm.bankms.domain.BankingTransaction} entity.
 */
public class BankingTransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer number;

    @NotNull
    private Instant date;

    @NotNull
    private Double ammount;

    @NotNull
    private TransactionType transactionType;

    private Integer extOriginAccount;

    private AccountType extOriginAccountType;

    private String extOriginAccountBank;

    private Integer extDestinationAccount;

    private AccountType extDestinationAccountType;

    private String extDestinationAccountBank;


    private Long originAccountId;

    private String originAccountNumber;

    private Long destinationAccountId;

    private String destinationAccountNumber;

    private Long bankingEntityId;

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

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getExtOriginAccount() {
        return extOriginAccount;
    }

    public void setExtOriginAccount(Integer extOriginAccount) {
        this.extOriginAccount = extOriginAccount;
    }

    public AccountType getExtOriginAccountType() {
        return extOriginAccountType;
    }

    public void setExtOriginAccountType(AccountType extOriginAccountType) {
        this.extOriginAccountType = extOriginAccountType;
    }

    public String getExtOriginAccountBank() {
        return extOriginAccountBank;
    }

    public void setExtOriginAccountBank(String extOriginAccountBank) {
        this.extOriginAccountBank = extOriginAccountBank;
    }

    public Integer getExtDestinationAccount() {
        return extDestinationAccount;
    }

    public void setExtDestinationAccount(Integer extDestinationAccount) {
        this.extDestinationAccount = extDestinationAccount;
    }

    public AccountType getExtDestinationAccountType() {
        return extDestinationAccountType;
    }

    public void setExtDestinationAccountType(AccountType extDestinationAccountType) {
        this.extDestinationAccountType = extDestinationAccountType;
    }

    public String getExtDestinationAccountBank() {
        return extDestinationAccountBank;
    }

    public void setExtDestinationAccountBank(String extDestinationAccountBank) {
        this.extDestinationAccountBank = extDestinationAccountBank;
    }

    public Long getOriginAccountId() {
        return originAccountId;
    }

    public void setOriginAccountId(Long bankingAccountId) {
        this.originAccountId = bankingAccountId;
    }

    public String getOriginAccountNumber() {
        return originAccountNumber;
    }

    public void setOriginAccountNumber(String bankingAccountNumber) {
        this.originAccountNumber = bankingAccountNumber;
    }

    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(Long bankingAccountId) {
        this.destinationAccountId = bankingAccountId;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(String bankingAccountNumber) {
        this.destinationAccountNumber = bankingAccountNumber;
    }

    public Long getBankingEntityId() {
        return bankingEntityId;
    }

    public void setBankingEntityId(Long bankingEntityId) {
        this.bankingEntityId = bankingEntityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BankingTransactionDTO bankingTransactionDTO = (BankingTransactionDTO) o;
        if (bankingTransactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankingTransactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankingTransactionDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", date='" + getDate() + "'" +
            ", ammount=" + getAmmount() +
            ", transactionType='" + getTransactionType() + "'" +
            ", extOriginAccount=" + getExtOriginAccount() +
            ", extOriginAccountType='" + getExtOriginAccountType() + "'" +
            ", extOriginAccountBank='" + getExtOriginAccountBank() + "'" +
            ", extDestinationAccount=" + getExtDestinationAccount() +
            ", extDestinationAccountType='" + getExtDestinationAccountType() + "'" +
            ", extDestinationAccountBank='" + getExtDestinationAccountBank() + "'" +
            ", originAccount=" + getOriginAccountId() +
            ", originAccount='" + getOriginAccountNumber() + "'" +
            ", destinationAccount=" + getDestinationAccountId() +
            ", destinationAccount='" + getDestinationAccountNumber() + "'" +
            ", bankingEntity=" + getBankingEntityId() +
            "}";
    }
}
