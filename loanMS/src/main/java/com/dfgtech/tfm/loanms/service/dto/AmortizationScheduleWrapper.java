package com.dfgtech.tfm.loanms.service.dto;

import java.util.List;

public class AmortizationScheduleWrapper {
	
	private List<AmortizationTableDTO> amortizationSchedule;

	public List<AmortizationTableDTO> getAmortizationSchedule() {
		return amortizationSchedule;
	}

	public void setAmortizationSchedule(List<AmortizationTableDTO> amortizationSchedule) {
		this.amortizationSchedule = amortizationSchedule;
	}
	
}
