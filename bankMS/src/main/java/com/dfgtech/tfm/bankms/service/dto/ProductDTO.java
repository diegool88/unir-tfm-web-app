package com.dfgtech.tfm.bankms.service.dto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.dfgtech.tfm.bankms.domain.enumeration.ProductCategory;
import com.dfgtech.tfm.bankms.domain.enumeration.Affirmation;

/**
 * A DTO for the {@link com.dfgtech.tfm.bankms.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

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

    @NotNull
    private ProductCategory category;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private Affirmation state;

    private Double interestRate;

    /**
     * Relaciones Microservicio administracion financiera
     */
    @ApiModelProperty(value = "Relaciones Microservicio administracion financiera")

    private Long bankingEntityId;

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

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
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

    public Affirmation getState() {
        return state;
    }

    public void setState(Affirmation state) {
        this.state = state;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
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

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", mnemonic='" + getMnemonic() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", category='" + getCategory() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", state='" + getState() + "'" +
            ", interestRate=" + getInterestRate() +
            ", bankingEntity=" + getBankingEntityId() +
            "}";
    }
}
