package com.dfgtech.tfm.loanms.service.dto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dfgtech.tfm.loanms.domain.AmortizationTable} entity.
 */
public class AmortizationTableDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer order;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private Double amount;

    @NotNull
    private Double interest;

    /**
     * Relaciones Microservicio de credito
     */
    @ApiModelProperty(value = "Relaciones Microservicio de credito")

    private Long loanProcessId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Long getLoanProcessId() {
        return loanProcessId;
    }

    public void setLoanProcessId(Long loanProcessId) {
        this.loanProcessId = loanProcessId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AmortizationTableDTO amortizationTableDTO = (AmortizationTableDTO) o;
        if (amortizationTableDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amortizationTableDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AmortizationTableDTO{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            ", dueDate='" + getDueDate() + "'" +
            ", amount=" + getAmount() +
            ", interest=" + getInterest() +
            ", loanProcess=" + getLoanProcessId() +
            "}";
    }
}
