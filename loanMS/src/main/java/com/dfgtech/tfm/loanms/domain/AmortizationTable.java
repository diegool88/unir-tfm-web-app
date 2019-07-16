package com.dfgtech.tfm.loanms.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AmortizationTable.
 */
@Entity
@Table(name = "amortization_table")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AmortizationTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @NotNull
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "interest", nullable = false)
    private Double interest;

    /**
     * Relaciones Microservicio de credito
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("amortizationTables")
    private LoanProcess loanProcess;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public AmortizationTable order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public AmortizationTable dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Double getAmount() {
        return amount;
    }

    public AmortizationTable amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInterest() {
        return interest;
    }

    public AmortizationTable interest(Double interest) {
        this.interest = interest;
        return this;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public LoanProcess getLoanProcess() {
        return loanProcess;
    }

    public AmortizationTable loanProcess(LoanProcess loanProcess) {
        this.loanProcess = loanProcess;
        return this;
    }

    public void setLoanProcess(LoanProcess loanProcess) {
        this.loanProcess = loanProcess;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmortizationTable)) {
            return false;
        }
        return id != null && id.equals(((AmortizationTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AmortizationTable{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            ", dueDate='" + getDueDate() + "'" +
            ", amount=" + getAmount() +
            ", interest=" + getInterest() +
            "}";
    }
}
