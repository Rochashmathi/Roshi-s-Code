/**
 * 
 */
package com.kryptolabs.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * @author nganewattage
 *
 */
public class DBConnection {

	private static Connection connection = null;

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://192.168.2.46:3306/investmentsdb";

	// Database credentials
	static final String userName = "root";
	static final String password = "password";

	static Connection getConnection() {

		if (connection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(DB_URL, userName, password) ;
				System.out.println("Connected to database successfully !");

			} catch (ClassNotFoundException cnfe) {
				System.out.println("Error loading class!");
				cnfe.printStackTrace();

			} catch (SQLException e) {
				System.out.println("Error connecting to the database!");
				e.printStackTrace();
			}
		}
		return connection;
	}
}
