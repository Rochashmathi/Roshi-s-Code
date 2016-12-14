/**
 * 
 */
package com.kryptolabs.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import com.kryptolabs.calculate.IRROutputData;

/**
 * @author nganewattage
 *
 */
public class IRROutputBean {

	public ArrayList<IRROutputData> irrOutputData;

	static Connection conn = null;

	@PostConstruct
	public String getIRRData() {
		
		irrOutputData = new ArrayList<IRROutputData>();

		try {
			conn = DBConnection.getConnection();

			String query = "SELECT iv.investment_vehicle_name as vehicle, i.investor_name as investor, irr.total_contributions, irr.total_distributions, irr.total_cash_flow, irr.xirr, irr.net_multiple, irr.date_of_calculation, irr.invested_since, iv.asset_class, iv.status FROM investmentsdb.irr_data irr inner join investmentsdb.investment_vehicle iv ON irr.investment_vehicle_id = iv.investment_vehicle_id inner join investmentsdb.investor i ON irr.investor_id = i.investor_id order by irr.invested_since asc";
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			IRROutputData dataItem;

			int rowcount = 0 ;
			if (resultSet.last()) {
				
				rowcount = resultSet.getRow();
				resultSet.beforeFirst(); 
			}
			
			System.out.println( "rowcount : " + rowcount);
			
			while (resultSet.next()) {

				String vehicle = resultSet.getString("vehicle");
				String investor = resultSet.getString("investor");
				double total_contributions = resultSet.getDouble("total_contributions");
				double total_distributions = resultSet.getDouble("total_distributions");
				double total_cash_flow = resultSet.getDouble("total_cash_flow");
				double xirr = resultSet.getDouble("xirr");
				double net_multiple = resultSet.getDouble("net_multiple");
				Date date_of_calculation = resultSet.getDate("date_of_calculation");
				String investedSince = resultSet.getString("invested_since");
				String assetClass = resultSet.getString("asset_class");
				String status = resultSet.getString("status");

				dataItem = new IRROutputData(vehicle,investor, total_cash_flow, total_contributions,
						total_distributions, xirr, net_multiple, date_of_calculation, investedSince, assetClass, status);

				irrOutputData.add(dataItem);
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return "irrOutput";
	}

	public ArrayList<IRROutputData> getIrrOutputData() {
		return irrOutputData;
	}

	public void setIrrOutputData(ArrayList<IRROutputData> irrOutputData) {
		this.irrOutputData = irrOutputData;
	}

}
