package com.dfgtech.tfm.rulesenginems.web.rest;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dfgtech.tfm.rulesenginems.service.dto.LoanProcessRulesDTO;

import java.time.LocalDate;
import java.time.Period;


@RestController
@RequestMapping("/api")
public class RulesEngineController {
	
	private final Logger log = LoggerFactory.getLogger(RulesEngineController.class);
	
	@Autowired
	private KieSession session;
	
	/**
     * {@code GET  /business-rules/loan-process} : get loan process business rules result.
     *
     * @return the {@link Boolean} with the result of business rules result.
     */
    @GetMapping("/business-rules/loan-process/{customerBirthDate}/{customerMonthlyIncome}/{customerGenre}/{loanRequestedAmmount}/{loanPeriod}")
    public Boolean getLoanProcessRulesResult(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate customerBirthDate, @PathVariable Double customerMonthlyIncome, @PathVariable String customerGenre, @PathVariable Double loanRequestedAmmount, @PathVariable Long loanPeriod) {
        log.debug("REST request to get all Business Rules loan process result");
        log.debug("Age: " + Period.between(customerBirthDate, LocalDate.now()).getYears());
        LoanProcessRulesDTO loanProcessRulesDTO = new LoanProcessRulesDTO();
        loanProcessRulesDTO.setCustomerAge(Period.between(customerBirthDate, LocalDate.now()).getYears());
        loanProcessRulesDTO.setCustomerGenre(customerGenre);
        loanProcessRulesDTO.setCustomerMonthlyIncome(customerMonthlyIncome);
        loanProcessRulesDTO.setLoanRequestedAmmount(loanRequestedAmmount);
        loanProcessRulesDTO.setLoanPeriod(loanPeriod.intValue());
        //loanProcessRulesDTO.setRulesResult(false); //Por defecto falla
        log.debug("Result Before: " + loanProcessRulesDTO.isRulesResult());
        session.setGlobal("logger", log);
        session.insert(loanProcessRulesDTO);
        session.fireAllRules();
        log.debug("Result After: " + loanProcessRulesDTO.isRulesResult());
        return loanProcessRulesDTO.isRulesResult();
    } 
}
