package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection connect() {
		Connection con = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:src/resources/database/Database.db");
			System.out.println("Database Updated");
		}catch (ClassNotFoundException | SQLException e) {
			System.out.println(e+"");
		}
		return con;
	}
}
