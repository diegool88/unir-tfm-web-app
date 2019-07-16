package com.dfgtech.tfm.bankms.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.dfgtech.tfm.bankms.domain.Fee} entity.
 */
public class FeeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    @Pattern(regexp = "[A-Z0-9]+")
    private String mnemonic;

    @NotNull
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z0-9\\s]+")
    private String name;

    private String description;

    private Double interestRate;

    private Double uniqueValue;

    /**
     * Relaciones Microservicio administracion financiera
     */
    @ApiModelProperty(value = "Relaciones Microservicio administracion financiera")

    private Set<ProductDTO> products = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getUniqueValue() {
        return uniqueValue;
    }

    public void setUniqueValue(Double uniqueValue) {
        this.uniqueValue = uniqueValue;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeeDTO feeDTO = (FeeDTO) o;
        if (feeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeeDTO{" +
            "id=" + getId() +
            ", mnemonic='" + getMnemonic() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", interestRate=" + getInterestRate() +
            ", uniqueValue=" + getUniqueValue() +
            "}";
    }
}
