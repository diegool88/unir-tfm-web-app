package com.dfgtech.tfm.bankms.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z]+")
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @NotNull
    @Size(max = 1)
    @Column(name = "sign", length = 1, nullable = false)
    private String sign;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "currency_product",
               joinColumns = @JoinColumn(name = "currency_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private Set<Product> products = new HashSet<>();

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

    public Currency name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public Currency sign(String sign) {
        this.sign = sign;
        return this;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Currency products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Currency addProduct(Product product) {
        this.products.add(product);
        product.getCurrencies().add(this);
        return this;
    }

    public Currency removeProduct(Product product) {
        this.products.remove(product);
        product.getCurrencies().remove(this);
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
        if (!(o instanceof Currency)) {
            return false;
        }
        return id != null && id.equals(((Currency) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sign='" + getSign() + "'" +
            "}";
    }
}
