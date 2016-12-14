/**
 * 
 */
package com.kryptolabs.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author nganewattage
 *
 */

public class InvestorDataBean {

	private String vehicle;
	private String investor;
	private String currency;
	private String cashFlowType;
	private double amount;
	private Date dateOfInvestment;

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/investmentsdb";

	// Database credentials
	static final String userName = "root";
	static final String passowrd = "password";

	static Connection conn = null;
	
	// Populate investments data in to DB
	public String submit() {
		
		try {
			conn = DBConnection.getConnection();
			
			String query = "INSERT INTO investments"
					+ "(investor_investment_vehicle, date, amount) VALUES ((SELECT investor_investment_vehicle_id FROM investor_investment_vehicle WHERE investor_id=(select investor_id FROM investor where investor_name=?) AND investment_vehicle_id=(select investment_vehicle_id FROM investment_vehicle WHERE investment_vehicle_name=?)),?,?)";

			PreparedStatement preparedStatement2 = conn.prepareStatement(query);
			preparedStatement2.setString(1, investor);
			preparedStatement2.setString(2, vehicle);
			preparedStatement2.setTimestamp(3, getInvestmentDate(dateOfInvestment));
			preparedStatement2.setDouble(4, getCashFlowAmount(cashFlowType, amount));
			
			preparedStatement2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return "investmentDataForm";
	}

	private double getCashFlowAmount(String cashFlowType, double amount) throws IllegalArgumentException {

		double cashFlowAmount = 0.0;

		if (cashFlowType.equalsIgnoreCase("Contribution")) {
			cashFlowAmount = -amount;
		} else if ((cashFlowType.equalsIgnoreCase("Distribution"))
				|| (cashFlowType.equalsIgnoreCase("NetCarryingValue"))) {
			cashFlowAmount = amount;
		} else {
			throw new IllegalArgumentException("Invalid Cash-flow type recieved !");
		}
		return cashFlowAmount;
	}

	private static java.sql.Timestamp getInvestmentDate(Date dateOfInvestment) {
		return new java.sql.Timestamp(dateOfInvestment.getTime());
	}
	
	public String calculateIRR() {
		// return to calculate IRR UI
		return "calculateIRR";
	}

	// Getters and setters
	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getInvestor() {
		return investor;
	}

	public void setInvestor(String investor) {
		this.investor = investor;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCashFlowType() {
		return cashFlowType;
	}

	public void setCashFlowType(String cashFlowType) {
		this.cashFlowType = cashFlowType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDateOfInvestment() {
		return dateOfInvestment;
	}

	public void setDateOfInvestment(Date dateOfInvestment) {
		this.dateOfInvestment = dateOfInvestment;
	}

}
