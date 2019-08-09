package com.dfgtech.tfm.loanms.business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dfgtech.tfm.loanms.service.dto.AmortizationTableDTO;

public class LoanProcessUtils {
	
	public static List<AmortizationTableDTO> calculateAmortizationSchedule(Double requestedAmount, Double annualInterestRate, LocalDate startDate, Long loanPeriod){
		List<AmortizationTableDTO> amortizationSchedule = new ArrayList<AmortizationTableDTO>();
		//Test Data
		AmortizationTableDTO dto1 = new AmortizationTableDTO();
		dto1.setAmount(12345.00);
		dto1.setDueDate(LocalDate.now());
		dto1.setInterest(123.45);
		dto1.setOrder(1);
		amortizationSchedule.add(dto1);
		AmortizationTableDTO dto2 = new AmortizationTableDTO();
		dto2.setAmount(112233.00);
		dto2.setDueDate(LocalDate.now());
		dto2.setInterest(345.45);
		dto2.setOrder(2);
		amortizationSchedule.add(dto2);
		AmortizationTableDTO dto3 = new AmortizationTableDTO();
		dto3.setAmount(11223355.00);
		dto3.setDueDate(LocalDate.now());
		dto3.setInterest(567.45);
		dto3.setOrder(3);
		amortizationSchedule.add(dto3);
		
		return amortizationSchedule;
	}

}
