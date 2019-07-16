package com.dfgtech.tfm.bankms.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fee.
 */
@Entity
@Table(name = "fee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fee implements Serializable {

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

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "unique_value")
    private Double uniqueValue;

    /**
     * Relaciones Microservicio administracion financiera
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "fee_product",
               joinColumns = @JoinColumn(name = "fee_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private Set<Product> products = new HashSet<>();

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

    public Fee mnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
        return this;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getName() {
        return name;
    }

    public Fee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Fee description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public Fee interestRate(Double interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getUniqueValue() {
        return uniqueValue;
    }

    public Fee uniqueValue(Double uniqueValue) {
        this.uniqueValue = uniqueValue;
        return this;
    }

    public void setUniqueValue(Double uniqueValue) {
        this.uniqueValue = uniqueValue;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Fee products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Fee addProduct(Product product) {
        this.products.add(product);
        product.getFees().add(this);
        return this;
    }

    public Fee removeProduct(Product product) {
        this.products.remove(product);
        product.getFees().remove(this);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fee)) {
            return false;
        }
        return id != null && id.equals(((Fee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fee{" +
            "id=" + getId() +
            ", mnemonic='" + getMnemonic() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", interestRate=" + getInterestRate() +
            ", uniqueValue=" + getUniqueValue() +
            "}";
    }
}
