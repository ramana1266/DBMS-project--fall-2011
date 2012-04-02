package com.telcor.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbconnection {

	private static Connection conn=null;
	
	private Dbconnection() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Connection getConnection(){
		
		if(conn==null){
		
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				
				conn = DriverManager.getConnection("jdbc:oracle:thin:@ora.csc.ncsu.edu:1523:orcl", "hlakshm", "001042486");
				
				return conn;
				
			} catch (ClassNotFoundException e) {
				
				System.out.println("Unable to create oracle connection");
				e.printStackTrace();
			
			} catch (SQLException e) {
				System.out.println("Unable to create oracle connection");
				e.printStackTrace();
			}
			
			return conn;
			
		}else
			
			return conn;
		
	}
	
	public static void closeConnection(){
		
		if(conn!=null){
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args){
		Connection c = Dbconnection.getConnection();
		try {
			Statement s = c.createStatement();
			int b = s.executeUpdate("INSERT INTO SERVICES VALUES(\'REPAIR PHONE\',19.99)");
			System.out.println("Affected rows : "+b);
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
