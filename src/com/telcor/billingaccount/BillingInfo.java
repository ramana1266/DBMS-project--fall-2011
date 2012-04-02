package com.telcor.billingaccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import com.telcor.db.Dbconnection;


public class BillingInfo {	
	
	protected static void generateBillingInfo(){
		
		ResultSet rs1=null;
		Calendar calendar = Calendar.getInstance();
		Connection conn = null;
		String sql1= "SELECT CID,STARTDATE FROM USAGERECORD";
		
		try{
		
		conn = Dbconnection.getConnection();
		Statement stmt1 = conn.createStatement();
		rs1 = stmt1.executeQuery(sql1);
		
		while(rs1.next()){		
			
			//Insert into duedates table
			calendar.add(Calendar.MONTH, 1);
			calendar.set(rs1.getDate(2).getYear(), rs1.getDate(2).getMonth(), 5);
			calendar.add(Calendar.MONTH, 1);
			
			//Create transaction
			conn.setAutoCommit(false);
			
			String sql3="INSERT INTO DUEDATES VALUES (to_char(?,'MON YYYY'),?)";
			PreparedStatement stmt3 = conn.prepareStatement(sql3);
			stmt3.setDate(1,rs1.getDate(2));
			stmt3.setDate(2,new java.sql.Date(calendar.getTime().getTime()));
			stmt3.executeUpdate();
			
			String sql2="INSERT INTO BillingAccount"+
						" Values(billingaccount_seq.NEXTVAL,?,"+
						" (SELECT usedminsuser1 + NVL(usedminsuser2,0) + NVL(usedminsuser3,0) + NVL(usedminsuser4,0) + "+ 
						" NVL(usedminsuser5,0) FROM UsageRecord "+
						" WHERE to_char(startdate,'MON YYYY')=to_char(?,'MON YYYY') AND cid=?), "+
					    " (SELECT (SELECT monthly_cost FROM Plans WHERE planid = (SELECT planid FROM Contract "+
						" WHERE cid = ?)) + "+
						" (SELECT ((SELECT GREATEST((SELECT (SELECT usedminsuser1 + NVL(usedminsuser2,0) + "+
						" NVL(usedminsuser3,0) + NVL(usedminsuser4,0) + NVL(usedminsuser5,0) FROM UsageRecord "+
						" WHERE to_char(startdate,'MON YYYY')=to_char(?,'MON YYYY') AND cid=?) -(SELECT fixed_minutes FROM Plans WHERE "+
						" planid IN (SELECT planid FROM Contract WHERE cid = "+
						" ?)) FROM DUAL),0) FROM DUAL) * "+
						" (SELECT extrausage_rates FROM Plans WHERE planid IN (SELECT planid FROM Contract WHERE cid "+
						" = ?))) FROM DUAL) FROM DUAL), "+
						" (SELECT SUM(fee) from Services where service_type IN "+
						" (SELECT service_type FROM CustomerService WHERE "+
						" to_char(request_date,'MON YYYY')=to_char(?,'MON YYYY') AND cid in (SELECT cid FROM Customer WHERE account_holder = ?))), "+
						" (SELECT SUM(fee) FROM Services where service_type IN "+
						" (SELECT service_type FROM ChangeRequest WHERE "+
						" to_char(request_date,'MON YYYY')=to_char(?,'MON YYYY') AND cid in (SELECT cid FROM Customer WHERE account_holder = ?))), "+
						" to_char(?,'MON YYYY'),NULL,NULL,NULL,NULL)";
			
			PreparedStatement stmt2 = conn.prepareStatement(sql2);
			stmt2.setLong(1,rs1.getLong(1));
			stmt2.setDate(2,rs1.getDate(2));
			stmt2.setLong(3,rs1.getLong(1));
			stmt2.setLong(4,rs1.getLong(1));
			stmt2.setDate(5,rs1.getDate(2));
			stmt2.setLong(6,rs1.getLong(1));
			stmt2.setLong(7,rs1.getLong(1));
			stmt2.setLong(8,rs1.getLong(1));
			stmt2.setDate(9,rs1.getDate(2));
			stmt2.setLong(10,rs1.getLong(1));
			stmt2.setDate(11,rs1.getDate(2));
			stmt2.setLong(12,rs1.getLong(1));
			stmt2.setDate(13,rs1.getDate(2));
			
			//System.out.println(sql2);
			
			int result = stmt2.executeUpdate();
			
			conn.commit();
			
		}
		
		}catch(SQLException e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
				
			try {
				conn.setAutoCommit(true);
				rs1.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Dbconnection.closeConnection();
		}
		
	}
	
	protected static void maintainBillingAccount(){
		
		ResultSet rs1=null,rs2=null;
		
		String sql1 = "Update BillingAccount "+
		"SET customerservice_fee = (customerservice_fee + ?)  "+
		" WHERE cid = ? and billing_period = to_char(?,'MON YYYY') AND "+
		" EXISTS (SELECT * FROM BillingAccount WHERE cid = ? and billing_period = to_char(?,'MON YYYY'))";
		
		String sql2= "SELECT C.CID,C.REQUEST_DATE,S.FEE FROM CUSTOMERSERVICE c,SERVICES s WHERE C.SERVICE_TYPE = S.SERVICE_TYPE";
		
		Connection conn = Dbconnection.getConnection();
		
		try {
			
		rs1 = conn.createStatement().executeQuery(sql2);
		
		while(rs1.next()){
			
			PreparedStatement stmt1 = conn.prepareStatement(sql1);
			stmt1.setFloat(1, rs1.getFloat(3));
			System.out.println(rs1.getFloat(3));
			stmt1.setLong(2, rs1.getLong(1));
			stmt1.setDate(3, rs1.getDate(2));
			stmt1.setLong(4, rs1.getLong(1));
			stmt1.setDate(5, rs1.getDate(2));
			
			int result = stmt1.executeUpdate();
			//System.out.println(result);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				
				rs1.close();
				Dbconnection.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		//Code to add cost for changerequest
		String sql3 = "Update BillingAccount "+
		"SET upgrading_fee = (upgrading_fee + ?)  "+
		" WHERE cid = ? and billing_period = to_char(?,'MON YYYY') AND "+
		" EXISTS (SELECT * FROM BillingAccount WHERE cid = ? and billing_period = to_char(?,'MON YYYY'))";
		
		String sql4= "SELECT C.CID,C.REQUEST_DATE,S.FEE FROM CHANGEREQUEST c,SERVICES s WHERE C.SERVICE_TYPE = S.SERVICE_TYPE";
		
		Connection conn11 = Dbconnection.getConnection();
		
		try {
			
		rs1 = conn11.createStatement().executeQuery(sql4);
		
		while(rs1.next()){
			
			PreparedStatement stmt1 = conn11.prepareStatement(sql3);
			stmt1.setFloat(1, rs1.getFloat(3));
			System.out.println(rs1.getFloat(3));
			stmt1.setLong(2, rs1.getLong(1));
			stmt1.setDate(3, rs1.getDate(2));
			stmt1.setLong(4, rs1.getLong(1));
			stmt1.setDate(5, rs1.getDate(2));
			
			int result = stmt1.executeUpdate();
			//System.out.println(result);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				
				rs1.close();
				Dbconnection.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
			 
	}
	
	
	public static void getMenu(){
		   int selectOption = 0;
			String opt=null;
			//Buffered reader to read the input
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("\n");
			System.out.println("Billing account Menu");
			System.out.println("--------------------");
			while(selectOption!=3){
				System.out.println("Select an option");
				System.out.println("----------------");		
				
				System.out.println("1. Generate Billing account");
				System.out.println("2. Maintain Billing account ");			
				System.out.println("3. Exit");

				try {
					opt=br.readLine();
					selectOption = Integer.parseInt(opt);
					
					switch(selectOption){
					case 1: 
						generateBillingInfo();
						break;
					case 2: 
						maintainBillingAccount();
						break;
					case 3: 
						break;
					default:
						System.out.println("Invalid Option");
						break;
					}
				}catch(IOException e){
					e.printStackTrace();
				}
			}
	  }

}
