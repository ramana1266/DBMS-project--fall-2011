package com.telcor.servicerecordmaintenance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import com.telcor.db.Dbconnection;


public class ServiceUsageRecord {
	
	private String usageRecordId;
	private String customerId;
	private Date startDate;
	private Date endDate;
	private int usedMins1;
	private int usedMins2;
	private int usedMins3;
	private int usedMins4;
	private int usedMins5;
	
	private static boolean isServiceAvailable(String customerId,Date startDate,Date endDate){
		
		try{
			
			Connection conn = Dbconnection.getConnection();
			
			String sql = "SELECT CID FROM USAGERECORD WHERE CID=? AND STARTDATE=? AND ENDDATE=?";
			
			PreparedStatement s = conn.prepareStatement(sql);
			s.setString(1, customerId);
			s.setDate(2,new java.sql.Date(startDate.getTime()));
			s.setDate(3,new java.sql.Date(endDate.getTime()));
			
			ResultSet rs = s.executeQuery();
			
			if(rs.next()){
				return true;
			}else{
				return false;
			}
			}catch (SQLException e) {
				e.printStackTrace();
				return false;
			}finally{
				Dbconnection.closeConnection();
			}
		
	}
	
	private static boolean isCustomerUnderContract(String customerId){
		
		try{
			
		Connection conn = Dbconnection.getConnection();
		
		String sql = "SELECT CID FROM CUSTOMER WHERE CID=\'"+customerId+"\'";
		
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(sql);
		
		if(rs.next()){
			return true;
		}else{
			return false;
		}
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			Dbconnection.closeConnection();
		}
		
	}
	
	private boolean updateServiceUsage(){
		
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		//Buffered reader to read the input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
	
			System.out.println("Enter the Customer Id:");
			customerId = br.readLine();
			
			//Code to check if customer is in contract
			if(!isCustomerUnderContract(customerId)){
				System.out.println("Customer does not have an existing contract\n");
				return false;
			}
			
			System.out.println("Enter the start date");
			try {
				startDate = (Date) formatter.parse(br.readLine());
			} catch (ParseException e1) {
				System.out.println("Invalid date input");
				e1.printStackTrace();
			} 
			
			System.out.println("Enter the end date");
			try {
				endDate = (Date) formatter.parse(br.readLine());
			} catch (ParseException e) {
				System.out.println("Invalid date input");
				e.printStackTrace();
			}
			
			//Check if service usage record exists for the customer and for the billing cycle..
			if(!isServiceAvailable(customerId, startDate, endDate)){
				System.out.println("Customer does not have a usage record for the given billing cycle");
				return false;
			}
				
			
			System.out.println("Enter Used minutes 1");
			usedMins1 = Integer.parseInt(br.readLine());
			System.out.println("Enter Used minutes 2");
			usedMins2 = Integer.parseInt(br.readLine());
			System.out.println("Enter Used minutes 3");
			usedMins3 = Integer.parseInt(br.readLine());
			System.out.println("Enter Used minutes 4");
			usedMins4 = Integer.parseInt(br.readLine());
			System.out.println("Enter Used minutes 5");
			usedMins5 = Integer.parseInt(br.readLine());
			
			String sql = "UPDATE USAGERECORD SET USERMINSUSER1=?,USERMINSUSER2=?,USERMINSUSER3=?,USERMINSUSER4=?,USERMINSUSER5=? WHERE " +
						 "CID= ? AND STARTDATE=? AND ENDDATE=?";
			
			Connection conn = Dbconnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, usedMins1);
			stmt.setInt(2, usedMins2);
			stmt.setInt(3, usedMins3);
			stmt.setInt(4, usedMins4);
			stmt.setInt(5, usedMins5);
			stmt.setString(6, customerId);
			stmt.setDate(7, new java.sql.Date(startDate.getTime()));
			stmt.setDate(8, new java.sql.Date(endDate.getTime()));
				
			int result = stmt.executeUpdate();
			
			if(result == 1){
				return true;
			}else
				return false;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Dbconnection.closeConnection();
		}
		
		return false;
		
	}
	
	private boolean enterServiceUsage(){
		
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		//Buffered reader to read the input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			//System.out.println("Enter the UsageRecord Id:");
			//usageRecordId = br.readLine();
			System.out.println("Enter the Customer Id:");
			customerId = br.readLine();
			
			//Code to check if customer is in contract
			if(!isCustomerUnderContract(customerId)){
				System.out.println("Customer does not have an existing contract\n");
				return false;
			}
			
			System.out.println("Enter the start date");
			try {
				startDate = (Date) formatter.parse(br.readLine());
			} catch (ParseException e1) {
				System.out.println("Invalid date input");
				e1.printStackTrace();
			} 
			
			System.out.println("Enter the end date");
			try {
				endDate = (Date) formatter.parse(br.readLine());
			} catch (ParseException e) {
				System.out.println("Invalid date input");
				e.printStackTrace();
			}
			
			//Check if service usage record exists for the customer and for the billing cycle..
			if(isServiceAvailable(customerId, startDate, endDate)){
				System.out.println("Customer already has a usage record for the given billing cycle");
				return false;
			}
			
			System.out.println("Enter Used minutes 1");
			usedMins1 = Integer.parseInt(br.readLine());
			System.out.println("Enter Used minutes 2");
			usedMins2 = Integer.parseInt(br.readLine());
			System.out.println("Enter Used minutes 3");
			usedMins3 = Integer.parseInt(br.readLine());
			System.out.println("Enter Used minutes 4");
			usedMins4 = Integer.parseInt(br.readLine());
			System.out.println("Enter Used minutes 5");
			usedMins5 = Integer.parseInt(br.readLine());
			
			String sql = "INSERT INTO USAGERECORD VALUES(usagerecord_seq.nextval,?,?,?,?,?,?,?,?)";
			
			Connection conn = Dbconnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			//stmt.setString(1, usageRecordId);
			stmt.setString(1, customerId);
			stmt.setDate(2, new java.sql.Date(startDate.getTime()));
			stmt.setDate(3, new java.sql.Date(endDate.getTime()));
			stmt.setInt(4, usedMins1);
			stmt.setInt(5, usedMins2);
			stmt.setInt(6, usedMins3);
			stmt.setInt(7, usedMins4);
			stmt.setInt(8, usedMins5);
			
			int result = stmt.executeUpdate();
			
			if(result == 1){
				return true;
			}else
				return false;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Dbconnection.closeConnection();
		}
		
		return false;
		
	}
	
	public void serviceUsageRecordMenu(){
		
		int selectOption = 0;
		String opt=null;
		boolean result=false;
		//Buffered reader to read the input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("\n");
		System.out.println("Service Usage Records Menu");
		System.out.println("---------------------------");
		
		while(selectOption!=3){
			System.out.println("Select an option");
			System.out.println("----------------");		
			
			System.out.println("1. Enter Service Usage Records");
			System.out.println("2. Update Service Usage Records");
			System.out.println("3. Exit");
			
			try {
				opt=br.readLine();
				selectOption = Integer.parseInt(opt);
				
				switch (selectOption) {
				
				//Code to enter new service usage record
				case 1:
					
					if(new ServiceUsageRecord().enterServiceUsage())
						System.out.println("Service Usage Record inserted successfully\n");
					else
						System.out.println("Service Usage Record insertion failed\n");	
					break;

				//Code to update service usage record
				case 2:
					if(new ServiceUsageRecord().updateServiceUsage())
						System.out.println("Service Usage Record update successful\n");
					else
						System.out.println("Service Usage Record updation failed\n");	
					break;
				
				case 3:
					break;
				

				default:
					System.out.println("Invalid option");
					break;
				}
				
			} catch (IOException e) {
				System.out.println("Enter a valid option");
				continue;
			}
			
		}
	}
		
}


