package com.dfgtech.tfm.rulesenginems.service.dto;

public class LoanProcessRulesDTO {
	
	private int customerAge;
	private double loanRequestedAmmount;
	private double customerMonthlyIncome;
	private String customerGenre;
	private boolean rulesResult;
	private int loanPeriod;
	
	public LoanProcessRulesDTO() {}
	
	public LoanProcessRulesDTO(int customerAge, double loanRequestedAmmount, double customerMonthlyIncome,
			String customerGenre) {
		super();
		this.customerAge = customerAge;
		this.loanRequestedAmmount = loanRequestedAmmount;
		this.customerMonthlyIncome = customerMonthlyIncome;
		this.customerGenre = customerGenre;
	}
	
	public int getCustomerAge() {
		return customerAge;
	}
	
	public void setCustomerAge(int customerAge) {
		this.customerAge = customerAge;
	}
	
	public double getLoanRequestedAmmount() {
		return loanRequestedAmmount;
	}
	
	public void setLoanRequestedAmmount(double loanRequestedAmmount) {
		this.loanRequestedAmmount = loanRequestedAmmount;
	}
	
	public double getCustomerMonthlyIncome() {
		return customerMonthlyIncome;
	}
	
	public void setCustomerMonthlyIncome(double customerMonthlyIncome) {
		this.customerMonthlyIncome = customerMonthlyIncome;
	}
	
	public String getCustomerGenre() {
		return customerGenre;
	}
	
	public void setCustomerGenre(String customerGenre) {
		this.customerGenre = customerGenre;
	}
	
	public boolean isRulesResult() {
		return rulesResult;
	}

	public void setRulesResult(boolean rulesResult) {
		this.rulesResult = rulesResult;
	}

	public int getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(int loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerAge;
		result = prime * result + ((customerGenre == null) ? 0 : customerGenre.hashCode());
		long temp;
		temp = Double.doubleToLongBits(customerMonthlyIncome);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + loanPeriod;
		temp = Double.doubleToLongBits(loanRequestedAmmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (rulesResult ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanProcessRulesDTO other = (LoanProcessRulesDTO) obj;
		if (customerAge != other.customerAge)
			return false;
		if (customerGenre == null) {
			if (other.customerGenre != null)
				return false;
		} else if (!customerGenre.equals(other.customerGenre))
			return false;
		if (Double.doubleToLongBits(customerMonthlyIncome) != Double.doubleToLongBits(other.customerMonthlyIncome))
			return false;
		if (loanPeriod != other.loanPeriod)
			return false;
		if (Double.doubleToLongBits(loanRequestedAmmount) != Double.doubleToLongBits(other.loanRequestedAmmount))
			return false;
		if (rulesResult != other.rulesResult)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LoanProcessRulesDTO [customerAge=" + customerAge + ", loanRequestedAmmount=" + loanRequestedAmmount
				+ ", customerMonthlyIncome=" + customerMonthlyIncome + ", customerGenre=" + customerGenre
				+ ", rulesResult=" + rulesResult + ", loanPeriod=" + loanPeriod + "]";
	}
	
}
