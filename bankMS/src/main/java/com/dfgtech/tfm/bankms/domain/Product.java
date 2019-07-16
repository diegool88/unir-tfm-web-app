package com.dfgtech.tfm.bankms.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.dfgtech.tfm.bankms.domain.enumeration.ProductCategory;

import com.dfgtech.tfm.bankms.domain.enumeration.Affirmation;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Pattern(regexp = "[A-Z0-9]+")
    @Column(name = "mnemonic", length = 10, nullable = false)
    private String mnemonic;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z0-9\\s]+")
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ProductCategory category;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private Affirmation state;

    @Column(name = "interest_rate")
    private Double interestRate;

    /**
     * Relaciones Microservicio administracion financiera
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("products")
    private BankingEntity bankingEntity;

    @ManyToMany(mappedBy = "products")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Fee> fees = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Currency> currencies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public Product mnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
        return this;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public Product category(ProductCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Product startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Product endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Affirmation getState() {
        return state;
    }

    public Product state(Affirmation state) {
        this.state = state;
        return this;
    }

    public void setState(Affirmation state) {
        this.state = state;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public Product interestRate(Double interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public BankingEntity getBankingEntity() {
        return bankingEntity;
    }

    public Product bankingEntity(BankingEntity bankingEntity) {
        this.bankingEntity = bankingEntity;
        return this;
    }

    public void setBankingEntity(BankingEntity bankingEntity) {
        this.bankingEntity = bankingEntity;
    }

    public Set<Fee> getFees() {
        return fees;
    }

    public Product fees(Set<Fee> fees) {
        this.fees = fees;
        return this;
    }

    public Product addFees(Fee fee) {
        this.fees.add(fee);
        fee.getProducts().add(this);
        return this;
    }

    public Product removeFees(Fee fee) {
        this.fees.remove(fee);
        fee.getProducts().remove(this);
        return this;
    }

    public void setFees(Set<Fee> fees) {
        this.fees = fees;
    }

    public Set<Currency> getCurrencies() {
        return currencies;
    }

    public Product currencies(Set<Currency> currencies) {
        this.currencies = currencies;
        return this;
    }

    public Product addCurrencies(Currency currency) {
        this.currencies.add(currency);
        currency.getProducts().add(this);
        return this;
    }

    public Product removeCurrencies(Currency currency) {
        this.currencies.remove(currency);
        currency.getProducts().remove(this);
        return this;
    }

    public void setCurrencies(Set<Currency> currencies) {
        this.currencies = currencies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", mnemonic='" + getMnemonic() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", category='" + getCategory() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", state='" + getState() + "'" +
            ", interestRate=" + getInterestRate() +
            "}";
    }
}
