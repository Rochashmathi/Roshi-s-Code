package com.kryptolabs.calculate;

import java.sql.Date;

public class IRROutputData {
	
	public String investor;
	
	public String investmentVehicle;
	
	public double cashFlow;
	
	public double totalContributions;
	
	public double totalDistributions;
	
	public double calculatedXIRR;
	
	public double calculatedNetMultiple;

	public Date dateOfCalculation;
	
	public String investedSince;
	
	public String assetClass;
	
	public String status;
	
	
	// Getters and Setters
	
	public String getInvestmentVehicle() {
		return investmentVehicle;
	}

	public void setInvestmentVehicle(String investmentVehicle) {
		this.investmentVehicle = investmentVehicle;
	}

	public double getCashFlow() {
		return cashFlow;
	}

	public void setCashFlow(double cashFlow) {
		this.cashFlow = cashFlow;
	}

	public double getTotalContributions() {
		return totalContributions;
	}

	public void setTotalContributions(double totalContributions) {
		this.totalContributions = totalContributions;
	}

	public double getTotalDistributions() {
		return totalDistributions;
	}

	public void setTotalDistributions(double totalDistributions) {
		this.totalDistributions = totalDistributions;
	}

	public double getCalculatedXIRR() {
		return calculatedXIRR;
	}

	public void setCalculatedXIRR(double calculatedXIRR) {
		this.calculatedXIRR = calculatedXIRR;
	}

	public double getCalculatedNetMultiple() {
		return calculatedNetMultiple;
	}

	public void setCalculatedNetMultiple(double calculatedNetMultiple) {
		this.calculatedNetMultiple = calculatedNetMultiple;
	}

	public String getInvestor() {
		return investor;
	}

	public void setInvestor(String investor) {
		this.investor = investor;
	}

	
	public Date getDateOfCalculation() {
		return dateOfCalculation;
	}

	public void setDateOfCalculation(Date dateOfCalculation) {
		this.dateOfCalculation = dateOfCalculation;
	}
	
	public String getInvestedSince() {
		return investedSince;
	}

	public void setInvestedSince(String investedSince) {
		this.investedSince = investedSince;
	}

	
	public String getAssetClass() {
		return assetClass;
	}

	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "IRROutputData [investor=" + investor + ", investmentVehicle=" + investmentVehicle + ", cashFlow="
				+ cashFlow + ", totalContributions=" + totalContributions + ", totalDistributions=" + totalDistributions
				+ ", calculatedXIRR=" + calculatedXIRR + ", calculatedNetMultiple=" + calculatedNetMultiple
				+ ", dateOfCalculation=" + dateOfCalculation + ", investedSince=" + investedSince + "]";
	}

	public IRROutputData(String investor, String investmentVehicle, double cashFlow, double totalContributions,
			double totalDistributions, double calculatedXIRR, double calculatedNetMultiple, Date dateOfCalculation,
			String investedSince, String assetClass, String status) {
		super();
		this.investor = investor;
		this.investmentVehicle = investmentVehicle;
		this.cashFlow = cashFlow;
		this.totalContributions = totalContributions;
		this.totalDistributions = totalDistributions;
		this.calculatedXIRR = calculatedXIRR;
		this.calculatedNetMultiple = calculatedNetMultiple;
		this.dateOfCalculation = dateOfCalculation; 
		this.investedSince = investedSince;
		this.assetClass = assetClass;
		this.status = status;
	}

	public IRROutputData() {
	}
	
}
