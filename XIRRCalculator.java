package com.kryptolabs.calculate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.kryptolabs.xirr.XIRR;
import com.kryptolabs.xirr.XIRRData;

/**
 * @author nganewattage
 *
 */
public class XIRRCalculator {
	
	private static double guess = 0.1;

	public static IRROutputData calculateXIRRPerVehicle(String vehicle, String investor, int totalNumberOfCashFlows, double[] values, double[] dates) {

		XIRRData data = new XIRRData(totalNumberOfCashFlows, guess, values, dates);

		double xirrValue = XIRR.xirr(data);
		double netMultiple = calculateNetMultiple(values);
		double totalContributions = calculateTotalContributions(values);
		double totalDistributions = calculateTotalDistributions(values);
		double cashFlow = totalContributions + totalDistributions;

		IRROutputData irrOutputData = new IRROutputData();
		irrOutputData.setInvestmentVehicle(vehicle);
		irrOutputData.setInvestor(investor);
		irrOutputData.setCalculatedXIRR((round((xirrValue - 1) * 100, 2)));
		irrOutputData.setCalculatedNetMultiple(round(netMultiple, 2));
		irrOutputData.setTotalContributions(totalContributions);
		irrOutputData.setTotalDistributions(totalDistributions);
		irrOutputData.setCashFlow(cashFlow);

		return irrOutputData;

	}

	private static double calculateTotalDistributions(double[] values) {

		double sumOfDistributions = 0;

		for (int i = 0; i < values.length; i++) {
			if (values[i] > 0) {
				sumOfDistributions = sumOfDistributions + values[i];
			}
		}

		return sumOfDistributions;
	}

	private static double calculateTotalContributions(double[] values) {

		double sumOfContributions = 0;

		for (int i = 0; i < values.length; i++) {
			if (values[i] < 0) {
				sumOfContributions = sumOfContributions + values[i];
			}
		}

		return sumOfContributions;
	}

	public static double formatStringValueToDate(String dateString) {

		// 2011-03-31 04:00:00.0
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		SimpleDateFormat yearFormater = new SimpleDateFormat("yyyy");

		Date dateObj = null;
		String yearString = null;

		try {
			dateObj = dateFormater.parse(dateString);
			yearString = yearFormater.format(dateObj);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateObj);

		int year = Integer.valueOf(yearString);
		int date = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH);

		return XIRRData
				.getExcelDateValue(new GregorianCalendar(year, month, date));
	}


	public static double calculateNetMultiple(double[] values) {

		double sumOfContributions = 0;
		double sumOfDistributions = 0;

		for (int i = 0; i < values.length; i++) {
			if (values[i] < 0) {
				sumOfContributions = sumOfContributions + values[i];
			} else {
				sumOfDistributions = sumOfDistributions + values[i];
			}
		}

		return sumOfDistributions / -(sumOfContributions);
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
