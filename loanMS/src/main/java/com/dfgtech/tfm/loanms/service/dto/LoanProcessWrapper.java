package com.dfgtech.tfm.loanms.service.dto;

import java.util.List;

public class LoanProcessWrapper {
	
	private LoanProcessDTO loanProcess;
	private List<AmortizationTableDTO> amortizationSchedule;
	private List<WarrantyDTO> warranties;
	
	public LoanProcessDTO getLoanProcess() {
		return loanProcess;
	}
	
	public void setLoanProcess(LoanProcessDTO loanProcess) {
		this.loanProcess = loanProcess;
	}
	
	public List<AmortizationTableDTO> getAmortizationSchedule() {
		return amortizationSchedule;
	}
	
	public void setAmortizationSchedule(List<AmortizationTableDTO> amortizationSchedule) {
		this.amortizationSchedule = amortizationSchedule;
	}
	
	public List<WarrantyDTO> getWarranties() {
		return warranties;
	}
	
	public void setWarranties(List<WarrantyDTO> warranties) {
		this.warranties = warranties;
	}

}
