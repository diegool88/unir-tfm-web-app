package com.dfgtech.tfm.bankms.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.dfgtech.tfm.bankms.domain.enumeration.TransactionType;

import com.dfgtech.tfm.bankms.domain.enumeration.AccountType;

/**
 * A BankingTransaction.
 */
@Entity
@Table(name = "banking_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BankingTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Column(name = "ammount", nullable = false)
    private Double ammount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "ext_origin_account")
    private Integer extOriginAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "ext_origin_account_type")
    private AccountType extOriginAccountType;

    @Column(name = "ext_origin_account_bank")
    private String extOriginAccountBank;

    @Column(name = "ext_destination_account")
    private Integer extDestinationAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "ext_destination_account_type")
    private AccountType extDestinationAccountType;

    @Column(name = "ext_destination_account_bank")
    private String extDestinationAccountBank;

    @ManyToOne
    @JsonIgnoreProperties("bankingTransactions")
    private BankingAccount originAccount;

    @ManyToOne
    @JsonIgnoreProperties("bankingTransactions")
    private BankingAccount destinationAccount;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("bankingTransactions")
    private BankingEntity bankingEntity;

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

    public BankingTransaction number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Instant getDate() {
        return date;
    }

    public BankingTransaction date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getAmmount() {
        return ammount;
    }

    public BankingTransaction ammount(Double ammount) {
        this.ammount = ammount;
        return this;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BankingTransaction transactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getExtOriginAccount() {
        return extOriginAccount;
    }

    public BankingTransaction extOriginAccount(Integer extOriginAccount) {
        this.extOriginAccount = extOriginAccount;
        return this;
    }

    public void setExtOriginAccount(Integer extOriginAccount) {
        this.extOriginAccount = extOriginAccount;
    }

    public AccountType getExtOriginAccountType() {
        return extOriginAccountType;
    }

    public BankingTransaction extOriginAccountType(AccountType extOriginAccountType) {
        this.extOriginAccountType = extOriginAccountType;
        return this;
    }

    public void setExtOriginAccountType(AccountType extOriginAccountType) {
        this.extOriginAccountType = extOriginAccountType;
    }

    public String getExtOriginAccountBank() {
        return extOriginAccountBank;
    }

    public BankingTransaction extOriginAccountBank(String extOriginAccountBank) {
        this.extOriginAccountBank = extOriginAccountBank;
        return this;
    }

    public void setExtOriginAccountBank(String extOriginAccountBank) {
        this.extOriginAccountBank = extOriginAccountBank;
    }

    public Integer getExtDestinationAccount() {
        return extDestinationAccount;
    }

    public BankingTransaction extDestinationAccount(Integer extDestinationAccount) {
        this.extDestinationAccount = extDestinationAccount;
        return this;
    }

    public void setExtDestinationAccount(Integer extDestinationAccount) {
        this.extDestinationAccount = extDestinationAccount;
    }

    public AccountType getExtDestinationAccountType() {
        return extDestinationAccountType;
    }

    public BankingTransaction extDestinationAccountType(AccountType extDestinationAccountType) {
        this.extDestinationAccountType = extDestinationAccountType;
        return this;
    }

    public void setExtDestinationAccountType(AccountType extDestinationAccountType) {
        this.extDestinationAccountType = extDestinationAccountType;
    }

    public String getExtDestinationAccountBank() {
        return extDestinationAccountBank;
    }

    public BankingTransaction extDestinationAccountBank(String extDestinationAccountBank) {
        this.extDestinationAccountBank = extDestinationAccountBank;
        return this;
    }

    public void setExtDestinationAccountBank(String extDestinationAccountBank) {
        this.extDestinationAccountBank = extDestinationAccountBank;
    }

    public BankingAccount getOriginAccount() {
        return originAccount;
    }

    public BankingTransaction originAccount(BankingAccount bankingAccount) {
        this.originAccount = bankingAccount;
        return this;
    }

    public void setOriginAccount(BankingAccount bankingAccount) {
        this.originAccount = bankingAccount;
    }

    public BankingAccount getDestinationAccount() {
        return destinationAccount;
    }

    public BankingTransaction destinationAccount(BankingAccount bankingAccount) {
        this.destinationAccount = bankingAccount;
        return this;
    }

    public void setDestinationAccount(BankingAccount bankingAccount) {
        this.destinationAccount = bankingAccount;
    }

    public BankingEntity getBankingEntity() {
        return bankingEntity;
    }

    public BankingTransaction bankingEntity(BankingEntity bankingEntity) {
        this.bankingEntity = bankingEntity;
        return this;
    }

    public void setBankingEntity(BankingEntity bankingEntity) {
        this.bankingEntity = bankingEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankingTransaction)) {
            return false;
        }
        return id != null && id.equals(((BankingTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BankingTransaction{" +
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
            "}";
    }
}
