package com.telcor.report;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.telcor.db.Dbconnection;


public class Report {
	
	String customerId;
	
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
	
	private void serviceUsageHistory(String customerId,Date startDate,Date endDate){
		
		Connection conn = Dbconnection.getConnection();
		ResultSet rs = null;
		
		String sql = "SELECT CID,TOTALUSER1_MIN,TOTALUSER2_MIN,TOTALUSER3_MIN,TOTALUSER4_MIN,TOTALUSER5_MIN,TOTALSERVICEFEE,TOTALUPGRADEFEE" +
		" FROM " +
		"(SELECT CID,SUM(USEDMINSUSER1) AS TOTALUSER1_MIN,SUM(USEDMINSUSER2) AS TOTALUSER2_MIN,SUM(USEDMINSUSER3) AS TOTALUSER3_MIN," +
		"SUM(USEDMINSUSER4) AS TOTALUSER4_MIN,SUM(USEDMINSUSER5) AS TOTALUSER5_MIN  FROM USAGERECORD WHERE CID=? AND STARTDATE>= ?" +
		" AND ENDDATE<= ? GROUP BY CID) " +
		"NATURAL JOIN " +
		"(SELECT CID,SUM(FEE) AS TOTALSERVICEFEE FROM CUSTOMERSERVICE,SERVICES WHERE CUSTOMERSERVICE.SERVICE_TYPE=SERVICES.SERVICE_TYPE" +
		" AND CID= ? AND REQUEST_DATE BETWEEN ? AND ? GROUP BY CID)" +
		" NATURAL JOIN " +
		"(SELECT CID,SUM(FEE) AS TOTALUPGRADEFEE FROM CHANGEREQUEST,SERVICES WHERE CHANGEREQUEST.SERVICE_TYPE=SERVICES.SERVICE_TYPE AND" +
		" CID=? AND REQUEST_DATE BETWEEN ? AND ? GROUP BY CID)";
		
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, customerId);
			stmt.setDate(2, new java.sql.Date(startDate.getTime()));
			stmt.setDate(3, new java.sql.Date(endDate.getTime()));
			stmt.setString(4, customerId);
			stmt.setDate(5, new java.sql.Date(startDate.getTime()));
			stmt.setDate(6, new java.sql.Date(endDate.getTime()));
			stmt.setString(7, customerId);
			stmt.setDate(8, new java.sql.Date(startDate.getTime()));
			stmt.setDate(9, new java.sql.Date(endDate.getTime()));
			
			rs = stmt.executeQuery();
			
			System.out.println("---\t--------------\t--------------\t--------------\t--------------\t--------------\t---------------\t---------------");
			System.out.println("CID\tTOTALMIN_USER1\tTOTALMIN_USER2\tTOTALMIN_USER3\tTOTALMIN_USER4\tTOTALMIN_USER5\tTOTALUPGRADEFEE\tTOTALSERVICEFEE");
			System.out.println("---\t--------------\t--------------\t--------------\t--------------\t--------------\t---------------\t---------------");
			
			while(rs.next()){
				
				System.out.print(rs.getString(1)+"\t");
				System.out.print(rs.getInt(2)+"\t");
				System.out.print(rs.getInt(3)+"\t");
				System.out.print(rs.getInt(4)+"\t");
				System.out.print(rs.getInt(5)+"\t");
				System.out.print(rs.getInt(6)+"\t");
				System.out.print(rs.getInt(7)+"\t");
				System.out.print(rs.getInt(8)+"\t");
				
			}
			
			System.out.println("---\t--------------\t--------------\t--------------\t--------------\t--------------\t---------------\t---------------");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				Dbconnection.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void responsibleServiceEngineerInfo(String staffId,Date startDate,Date endDate){
		
		ResultSet rs = null;
		Connection conn = Dbconnection.getConnection();
		String sql = "SELECT CUSTOMER.* FROM CUSTOMER JOIN CUSTOMERSERVICE ON CUSTOMER.CID=CUSTOMERSERVICE.CID AND SERVICEENGINEERID = ? AND REQUEST_DATE BETWEEN ? AND ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, staffId);
			stmt.setDate(2,new java.sql.Date(startDate.getTime()));
			stmt.setDate(3,new java.sql.Date(endDate.getTime()));
			
			rs = stmt.executeQuery();
			
			System.out.println("---\t---\t\t----\t---\t\t------\t------------\t---------");
			System.out.println("CID\tSSN\t\tNAME\tDOB\t\tGENDER\tPHONE_NUMBER\tADDRESS");
			System.out.println("---\t---\t\t----\t---\t\t------\t------------\t---------");
			
			
			while(rs.next()){
				
				System.out.print(rs.getString(1)+"\t");
				System.out.print(rs.getString(2)+"\t");
				System.out.print(rs.getString(3)+"\t");
				System.out.print(rs.getDate(4)+"\t");
				System.out.print(rs.getString(5)+"\t");
				System.out.print(rs.getString(6)+"\t");
				System.out.println(rs.getString(7)+"\n");
				
			}
			
			System.out.println("---\t---\t\t----\t---\t\t------\t------------\t---------");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{		
			try {
				rs.close();
				Dbconnection.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void responsibleSalesmanInfo(String staffId,Date startDate,Date endDate){
		
		ResultSet rs = null;
		Connection conn = Dbconnection.getConnection();
		String sql = "SELECT CUSTOMER.* FROM CUSTOMER JOIN CONTRACT ON CUSTOMER.CID=CONTRACT.CID AND CONTRACT.RESPONSIBLE_SALESMAN = ? AND STARTDATE BETWEEN ? AND ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, staffId);
			stmt.setDate(2,new java.sql.Date(startDate.getTime()));
			stmt.setDate(3,new java.sql.Date(endDate.getTime()));
			
			rs = stmt.executeQuery();
			
			System.out.println("---\t---\t\t----\t---\t\t------\t------------\t---------");
			System.out.println("CID\tSSN\t\tNAME\tDOB\t\tGENDER\tPHONE_NUMBER\tADDRESS");
			System.out.println("---\t---\t\t----\t---\t\t------\t------------\t---------");
			
			
			while(rs.next()){
				
				System.out.print(rs.getString(1)+"\t");
				System.out.print(rs.getString(2)+"\t");
				System.out.print(rs.getString(3)+"\t");
				System.out.print(rs.getDate(4)+"\t");
				System.out.print(rs.getString(5)+"\t");
				System.out.print(rs.getString(6)+"\t");
				System.out.println(rs.getString(7)+"\n");
				
			}
			
			System.out.println("---\t---\t\t----\t---\t\t------\t------------\t---------");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{		
			try {
				rs.close();
				Dbconnection.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}

	public void reportMenu(){
		
		int selectOption = 0;
		String opt=null,s=null;
		boolean result=false;
		Date startDate = null,endDate = null;
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		//Buffered reader to read the input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("\nReports");
		System.out.println("---------");
		
		while(selectOption!=5){
			System.out.println("\nSelect an option");
			System.out.println("------------------");		
			
			System.out.println("1. Service usage history for customer for a given period");
			System.out.println("2. Customers a Salesman is responsible for a given period");
			System.out.println("3. Customers a service engineer is responsible for a given period");
			System.out.println("4. Telecor staff information based on role");
			System.out.println("5. Exit");
			
			try {
				opt=br.readLine();
				selectOption = Integer.parseInt(opt);
				
				switch (selectOption) {
				
				//Code to display service usage history of a customer
				case 1:
					
					System.out.println("Enter Customer id : ");
					customerId = br.readLine();
					
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
					
					if(isCustomerUnderContract(customerId))
						serviceUsageHistory(customerId,startDate,endDate);
					else
						System.out.println("Invalid customer Id");
					break;
					
				
				//Code to display salesman and his customers 
				case 2:
					
					System.out.println("Enter the salesman id");
					s=br.readLine();
					
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
					
					//Call the method to print the records
					responsibleSalesmanInfo(s,startDate,endDate);
					
					break;				
				
				case 3:
					
					System.out.println("Enter the service engineer id");
					s=br.readLine();
					
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
					
					//Call the method to print the records
					responsibleServiceEngineerInfo(s,startDate,endDate);
					
					break;
					
				case 4:
					
					ResultSet rs = null;
					Connection conn = Dbconnection.getConnection();
					String sql = "SELECT * FROM STAFF ORDER BY JOB_TITLE";
					try {
						Statement stmt = conn.createStatement();
						rs = stmt.executeQuery(sql);
						
						while(rs.next()){
							System.out.print(rs.getString(1)+"\t");
							System.out.print(rs.getString(2)+"\t");
							System.out.print(rs.getInt(3)+"\t");
							System.out.print(rs.getString(4)+"\t");
							System.out.print(rs.getString(5)+"\t");
							System.out.print(rs.getString(6)+"\t");
							System.out.print(rs.getString(7)+"\t");
							System.out.println(rs.getString(8));
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							Dbconnection.closeConnection();
							rs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}
					
					break;
					
				case 5:	
					
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
