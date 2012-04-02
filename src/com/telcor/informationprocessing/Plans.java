package com.telcor.informationprocessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.telcor.db.Dbconnection;

public class Plans {
    String planid;
    String service_type;
    String montly_cost;
    String fixed_minutes;
    String extrausage_rate;
	public Plans() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Plans(String planid, String serviceType, String montlyCost,
			String fixedMinutes, String extrausageRate) {
		super();
		this.planid = planid;
		service_type = serviceType;
		montly_cost = montlyCost;
		fixed_minutes = fixedMinutes;
		extrausage_rate = extrausageRate;
	}
	public String getPlanid() {
		return planid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String serviceType) {
		service_type = serviceType;
	}
	public String getMontly_cost() {
		return montly_cost;
	}
	public void setMontly_cost(String montlyCost) {
		montly_cost = montlyCost;
	}
	public String getFixed_minutes() {
		return fixed_minutes;
	}
	public void setFixed_minutes(String fixedMinutes) {
		fixed_minutes = fixedMinutes;
	}
	public String getExtrausage_rate() {
		return extrausage_rate;
	}
	public void setExtrausage_rate(String extrausageRate) {
		extrausage_rate = extrausageRate;
	}
    public int ChangeRatePlan(){
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       Connection conn = Dbconnection.getConnection();
 	   Statement stmt = null;
 	   PreparedStatement pstmt = null;
 	   try {
 		  stmt = conn.createStatement();
 		  pstmt = conn.prepareStatement("SELECT planid from PLANS");
 		  ResultSet rs = pstmt.executeQuery();
 		  System.out.println("Available Plans");
 		  while(rs.next()){
 			  String t = rs.getString("planid");
 			  System.out.println(t);
 		  }
 		  System.out.println("Select the plan:");
 		  planid = br.readLine();
 		  System.out.println("Enter service type:");
 		  service_type = br.readLine();
 		 System.out.println("Montly Cost:");
 		  montly_cost = br.readLine();
 		 System.out.println("Fixed Minutes:");
 		 fixed_minutes = br.readLine();
 		 System.out.println("Extra Usage:");
 		 extrausage_rate = br.readLine();
 		 
 		pstmt = conn.prepareStatement("UPDATE PLANS SET  service_type=? , montly_cost=? , fixed_minutes=? , extrausage_rate=? WHERE planid=?");
 		pstmt.setString(1, service_type);
 		pstmt.setString(2, montly_cost);
 		pstmt.setString(3, fixed_minutes);
 		pstmt.setString(4, extrausage_rate);
 		pstmt.setString(5, planid);
 	    int result = pstmt.executeUpdate();
 	    System.out.println("Plan Changed Successfully");
 		return result;
 		
 	   }catch(IOException e){
 		   e.printStackTrace();
 	   }
 	   catch(SQLException e){
 		   e.printStackTrace();
 	  }finally {
 		 Dbconnection.closeConnection();
 	  }
 	   return 0;
    }
    
}
