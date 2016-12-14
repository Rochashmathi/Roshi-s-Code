/**
 * 
 */
package com.kryptolabs.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;

import com.kryptolabs.calculate.IRROutputData;
import com.kryptolabs.calculate.XIRRCalculator;

/**
 * @author nganewattage
 *
 */
public class CalculateIRRBean {

	private String vehicle;
	private String investor;

	private String investedSince;

	static Connection conn = null;

	private static final String SEPARATOR = " ";

	public void calculateIRR() {

		double amounts[] = null;
		double dates[] = null;

		System.out.println("calculateing IRR for : " + vehicle + " " + investor);
		try {
			conn = DBConnection.getConnection();

			String query = "SELECT iiv.investor_id as investor, iiv.investment_vehicle_id as vehicle, i.date, i.amount FROM investmentsdb.investments i inner join investor_investment_vehicle iiv on i.investor_investment_vehicle = iiv.investor_investment_vehicle_id where i.investor_investment_vehicle = (SELECT investor_investment_vehicle_id FROM investor_investment_vehicle WHERE investor_id=(select investor_id FROM investor where investor_name=?) AND investment_vehicle_id=(select investment_vehicle_id FROM investment_vehicle WHERE investment_vehicle_name=?)) order by date asc";

			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, investor);
			statement.setString(2, vehicle);

			ResultSet resultSet = statement.executeQuery();

			int rowcount = 0;
			int investor_id = 0;
			int vehicle_id = 0;

			if (resultSet.last()) {
				investor_id = resultSet.getInt("investor");
				vehicle_id = resultSet.getInt("vehicle");
				rowcount = resultSet.getRow();
				resultSet.beforeFirst(); // not rs.first() because the rs.next()
											// below will move on, missing the
											// first element
			}

			System.out.println("rowcount : " + rowcount);

			amounts = new double[rowcount];
			dates = new double[rowcount];

			int index = 0;
			while (resultSet.next()) {

				if (resultSet.isFirst()) {

					// as the results are ordered by date ASC, the first record
					// represents the initial investment and hence picking
					// the invested_since date from that record.
					Date date = resultSet.getDate("date");
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH);
					String quarter = getQuarterForMonth(month);
					investedSince = year + SEPARATOR + quarter;
				}

				double amount = resultSet.getDouble("amount");
				amounts[index] = amount;

				String dateOfInvestment = resultSet.getString("date");
				double date = XIRRCalculator.formatStringValueToDate(dateOfInvestment);
				dates[index] = date;

				index++;
			}

			System.out.println("dates : " + Arrays.toString(dates));
			System.out.println("amounts : " + Arrays.toString(amounts));

			IRROutputData outputData = XIRRCalculator.calculateXIRRPerVehicle(vehicle, investor, amounts.length,
					amounts, dates);
			System.out.println(outputData);

			// Insert calculated data in to database

			String query2 = "INSERT INTO irr_data (investor_id, investment_vehicle_id, total_contributions, total_distributions, total_cash_flow, xirr, net_multiple, date_of_calculation, invested_since) VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(query2);
			preparedStatement.setInt(1, investor_id);
			preparedStatement.setInt(2, vehicle_id);
			preparedStatement.setDouble(3, outputData.getTotalContributions());
			preparedStatement.setDouble(4, outputData.getTotalDistributions());
			preparedStatement.setDouble(5, outputData.getCashFlow());
			preparedStatement.setDouble(6, outputData.getCalculatedXIRR());
			preparedStatement.setDouble(7, outputData.getCalculatedNetMultiple());
			preparedStatement.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime()));
			preparedStatement.setString(9, investedSince);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}

	private String getQuarterForMonth(int month) {

		String quarter = "";

		switch (month) {
		case 0:
		case 1:
		case 2:
			quarter = "Q1";
			break;
		case 3:
		case 4:
		case 5:
			quarter = "Q2";
			break;
		case 6:
		case 7:
		case 8:
			quarter = "Q3";
			break;
		case 9:
		case 10:
		case 11:
			quarter = "Q4";
			break;
		}

		return quarter;
	}

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

	public String getInvestedSince() {
		return investedSince;
	}

	public void setInvestedSince(String investedSince) {
		this.investedSince = investedSince;
	}

}
