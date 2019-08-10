package com.dfgtech.tfm.loanms.business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dfgtech.tfm.loanms.service.dto.AmortizationTableDTO;

public class LoanProcessUtils {
	
	public static List<AmortizationTableDTO> calculateAmortizationSchedule(Double requestedAmount, Double annualInterestRate, LocalDate startDate, Long loanPeriod){
		List<AmortizationTableDTO> amortizationSchedule = new ArrayList<AmortizationTableDTO>();
		Double principal = requestedAmount; 
		Long length = loanPeriod * 12; 
		Double interest = annualInterestRate;
		Double monthlyInterest = interest / (12 * 100); 
		Double monthlyPayment = principal * ( monthlyInterest / ( 1 - Math.pow((1 + monthlyInterest), (length * -1))));

		for (int x = 1; x <= length; x++) { 
			AmortizationTableDTO dto = new AmortizationTableDTO();
			Double amountInterest = principal * monthlyInterest; 
			Double amountPrincipal = monthlyPayment - amountInterest;
			principal = principal - amountPrincipal;
			dto.setAmount(amountPrincipal);
			dto.setDueDate(startDate.plusMonths(x-1));
			dto.setInterest(amountInterest);
			dto.setOrder(x);
			amortizationSchedule.add(dto);
		}
		
		return amortizationSchedule;
	}

}
