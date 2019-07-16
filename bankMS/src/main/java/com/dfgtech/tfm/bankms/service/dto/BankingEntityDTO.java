package com.dfgtech.tfm.bankms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dfgtech.tfm.bankms.domain.BankingEntity} entity.
 */
public class BankingEntityDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    @Pattern(regexp = "[A-Z0-9]+")
    private String mnemonic;

    @NotNull
    @Size(min = 5, max = 40)
    @Pattern(regexp = "[A-Za-z0-9\\s]+")
    private String name;

    @Size(min = 5, max = 60)
    @Pattern(regexp = "[A-Za-z0-9\\s]+")
    private String description;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BankingEntityDTO bankingEntityDTO = (BankingEntityDTO) o;
        if (bankingEntityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankingEntityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankingEntityDTO{" +
            "id=" + getId() +
            ", mnemonic='" + getMnemonic() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
