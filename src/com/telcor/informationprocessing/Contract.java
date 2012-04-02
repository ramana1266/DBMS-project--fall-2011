package com.telcor.informationprocessing;

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
import java.sql.Date;

import com.telcor.db.Dbconnection;


public class Contract {
   String contractid;
   String cid;
   String planid;
   String cellphone;
   Date startdate;
   Date enddate;
   String res_salesman;
   String status;
   int numlines;
public Contract() {
	super();
	// TODO Auto-generated constructor stub
}
public String getContractid() {
	return contractid;
}
public void setContractid(String contractid) {
	this.contractid = contractid;
}
public String getCid() {
	return cid;
}
public void setCid(String cid) {
	this.cid = cid;
}
public String getPlanid() {
	return planid;
}
public void setPlanid(String planid) {
	this.planid = planid;
}
public String getCellphone() {
	return cellphone;
}
public void setCellphone(String cellphone) {
	this.cellphone = cellphone;
}
public Date getStartdate() {
	return startdate;
}
public void setStartdate(Date startdate) {
	this.startdate = startdate;
}
public Date getEnddate() {
	return enddate;
}
public void setEnddate(Date enddate) {
	this.enddate = enddate;
}
public String getRes_salesman() {
	return res_salesman;
}
public void setRes_salesman(String resSalesman) {
	res_salesman = resSalesman;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public int getNumlines() {
	return numlines;
}
public void setNumlines(int numlines) {
	this.numlines = numlines;
}
private int insertContract(){
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	Contract c = new Contract();
	try {
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd-MMM-yyyy");
		System.out.println("Enter Contract id:");
		c.setContractid(br.readLine());
		System.out.println("Enter Customer id:");
		c.setCid(br.readLine());
		System.out.println("Enter Plan id:");
		c.setPlanid(br.readLine());
		System.out.println("Enter Cellphone :");
		c.setCellphone(br.readLine());
		System.out.println("Enter Start date:");
		c.setStartdate(new java.sql.Date((formatter.parse(br.readLine())).getTime())); 
		System.out.println("Enter End date:");
		c.setEnddate(new java.sql.Date((formatter.parse(br.readLine())).getTime())); 
		System.out.println("Enter Responsible Salesman Id:");
		c.setRes_salesman(br.readLine());
		System.out.println("Enter Status:");
		c.setStatus(br.readLine());
		System.out.println("Enter Number of Lines:");
		c.setNumlines(Integer.parseInt(br.readLine())); 		
	} catch(IOException e){
		   e.printStackTrace();
	   }catch(ParseException e){
		   e.printStackTrace();
	   }
	   Connection conn = Dbconnection.getConnection();
	   Statement stmt = null;
	   PreparedStatement pstmt = null;
	   try {
		  stmt = conn.createStatement();
		  pstmt = conn.prepareStatement("INSERT INTO CONTRACT VALUES(?,?,?,?,?,?,?,?,?)");
		  pstmt.setString(1, c.getContractid());
		  pstmt.setString(2, c.getCid());
		  pstmt.setString(3, c.getPlanid());
		  pstmt.setString(4, c.getCellphone());
		  pstmt.setDate(5, c.getStartdate());
		  pstmt.setDate(6, c.getEnddate());
		  pstmt.setString(7, c.getRes_salesman());
		  pstmt.setString(8, c.getStatus());
		  pstmt.setInt(9, c.getNumlines());
		  int result = pstmt.executeUpdate();
		  return result;
	   }catch(SQLException e){
		   e.printStackTrace();
	   }finally {
		   Dbconnection.closeConnection();
	   }
	   return 0;
}
private int updateContract(String id){
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	Contract c = new Contract();
	try {
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		System.out.println("Enter Customer id:");
		c.setCid(br.readLine());
		System.out.println("Enter Plan id:");
		c.setPlanid(br.readLine());
		System.out.println("Enter Cellphone :");
		c.setCellphone(br.readLine());
		System.out.println("Enter Start date:");
		c.setStartdate(new java.sql.Date((formatter.parse(br.readLine())).getTime())); 
		System.out.println("Enter End date:");
		c.setEnddate(new java.sql.Date((formatter.parse(br.readLine())).getTime())); 
		System.out.println("Enter Responsible Salesman Id:");
		c.setRes_salesman(br.readLine());
		System.out.println("Enter Status:");
		c.setStatus(br.readLine());
		System.out.println("Enter Number of Lines:");
		c.setNumlines(Integer.parseInt(br.readLine())); 		
	} catch(IOException e){
		   e.printStackTrace();
	   }catch(ParseException e){
		   e.printStackTrace();
	   }
	   Connection conn = Dbconnection.getConnection();
	   Statement stmt = null;
	   PreparedStatement pstmt = null;
	   try {
		  stmt = conn.createStatement();
		  pstmt = conn.prepareStatement("UPDATE CONTRACT SET cid=? , planid=? , cellphone=? , startdate=? , enddate=?, responsible_salesman=?, status=? ,num_lines=? WHERE contractid=?");
		 
		  pstmt.setString(1, c.getCid());
		  pstmt.setString(2, c.getPlanid());
		  pstmt.setString(3, c.getCellphone());
		  pstmt.setDate(4, c.getStartdate());
		  pstmt.setDate(5, c.getEnddate());
		  pstmt.setString(6, c.getRes_salesman());
		  pstmt.setString(7, c.getStatus());
		  pstmt.setInt(8, c.getNumlines());
		  pstmt.setString(9, id);
		  int result = pstmt.executeUpdate();
		  return result;
	   }catch(SQLException e){
		   e.printStackTrace();
	   }finally {
		   Dbconnection.closeConnection();
	   }
	   return 0;
}
private boolean findContract(String id){
	PreparedStatement pstmt = null;
	try {
		 Connection conn = Dbconnection.getConnection();
		 pstmt = conn.prepareStatement("SELECT contractid FROM contract WHERE contractid=?");
		 pstmt.setString(1, id);
		 ResultSet rs = pstmt.executeQuery();
		 if(rs.next()){
			   rs.close();
	    	   return true;
	       }
	     else {
	    	   rs.close();
	    	   return false;
	      }
	       
		}catch(SQLException e){
			   e.printStackTrace();
			  
			   return false;
		   }finally{
			   Dbconnection.closeConnection();
		}
}
private int deleteContract(String id){
	Connection conn = Dbconnection.getConnection();
	   Statement stmt = null;
	   PreparedStatement pstmt = null;
	   try {
		   stmt = conn.createStatement();
		   pstmt = conn.prepareStatement("DELETE FROM CONTRACT WHERE contractid=?");
		  
		   pstmt.setString(1, id);
		   int rs = pstmt.executeUpdate();
		   return rs;
	   }catch(SQLException e){
		   e.printStackTrace();
	   }finally{
			Dbconnection.closeConnection();
			
		}
	  return 0;
}
public static void getMenu(){
	   int selectOption = 0;
		String opt=null;
		//Buffered reader to read the input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("\n");
		System.out.println("Contract Menu");
		System.out.println("---------------------------");
		while(selectOption!=4){
			System.out.println("Select an option");
			System.out.println("----------------");		
			
			System.out.println("1. Add Contract");
			System.out.println("2. Update Contract");
			System.out.println("3. Delete Contract");
			System.out.println("4. Exit");
			try {
				opt=br.readLine();
				selectOption = Integer.parseInt(opt);
				
				switch(selectOption){
				case 1: 
					int r= new Contract().insertContract();
					if(r==1){
						System.out.println("Insert successful");
					}
					else {
						System.out.println("Insert Unsuccessful");
					}
					break;
				case 2: 
					System.out.println("Enter Contract id: ");
					String id = br.readLine();
					boolean b=  new Contract().findContract(id);
					if(b){
						
						int r1= new Contract().updateContract(id);
						if(r1==1)
							System.out.println("Update Successful");
						else
							System.out.println("Update Unsuccessful");
						
					}
					break;
				case 3: 
					System.out.println("Enter Contract id: ");
					String id1 = br.readLine();
					boolean b1 = new Contract().findContract(id1);
					if(b1){
						int r2= new Contract().deleteContract(id1);
						if(r2==1)
							System.out.println("Delete Successful");
						else 
							System.out.println("Delete Unsuccessful");
					}
					break;
				case 4:
					System.out.println("Invalid Option");
					break;
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
  }
  public int checkNumline(){
	   Connection conn = Dbconnection.getConnection();
	   Statement stmt = null;
	   PreparedStatement pstmt = null;
	   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   String id;
	   try{
		   System.out.println("Enter Contract id: ");
		   id = br.readLine();
		   stmt = conn.createStatement();
		   pstmt = conn.prepareStatement("SELECT num_lines FROM CONTRACT WHERE contractid=?");
		   pstmt.setString(1, id);
		
		   ResultSet rs = pstmt.executeQuery();
           rs.next();	
		 
		   int temp = rs.getInt(1);
		   System.out.println(temp);
  		   
	        if(temp<5){
	        	  temp=temp+1;
	        	  pstmt= conn.prepareStatement("UPDATE CONTRACT SET num_lines=? WHERE contractid=?" );
	        	  pstmt.setInt(1, temp);
	        	  pstmt.setString(2, id);
	        	  int result = pstmt.executeUpdate();
	        	  
	        	  pstmt = conn.prepareStatement("SELECT CELLPHONE FROM CONTRACT WHERE contractid=?");
				  pstmt.setString(1, id);
				  ResultSet rs1 = pstmt.executeQuery();
				  rs1.next();
				  
				  String name = rs1.getString(1);
				  System.out.println("name"); 
				   new Warehouse().decreaseCellphoneQuantity(name);
				  
			      Dbconnection.closeConnection();
			    // new Customer().insertCustomer();
			      
			      new Customer().insertCustomer();
			      
			      return result;
		   }
		   else {
			   System.out.println("5 users at max for a family plan . Cannot add extra line");
		   }
	       return 1;
	   }
        catch(IOException e){
        	e.printStackTrace();
        }
        catch(SQLException e){
        	e.printStackTrace();
        }finally {
        	Dbconnection.closeConnection();
        }
        return 0;   
  }
  public int upgradeCellphone(){
	   
	  DateFormat formatter;
		formatter = new SimpleDateFormat("dd-MMM-yyyy");
	   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   String cell,id;
	   try{

		   System.out.println("Enter Customer id: ");
		   id=br.readLine();
		   System.out.println("Enter Cell phone to which you want to upgrade to:");
		   cell=br.readLine();
		  boolean b= new Warehouse().isCellphoneAvailable(cell);
		
		   if(b){
			   System.out.println("hi");
			   Connection conn = Dbconnection.getConnection();

			   Statement stmt = null;
			   PreparedStatement pstmt = null;
			   stmt = conn.createStatement();
			   pstmt = conn.prepareStatement("UPDATE CONTRACT SET CELLPHONE=? WHERE CID=?");
			   pstmt.setString(1, cell);
			   pstmt.setString(2, id);
			   int result = pstmt.executeUpdate();
			   
			  
			   Date d;
			   d = new java.sql.Date((formatter.parse("02-DEC-11")).getTime()); 
			   System.out.println("Cellphone Upgraded");
			   pstmt= conn.prepareStatement("INSERT INTO ChangeRequest VALUES(upgraderequest_seq.nextVal,?,?,?,?,?)");
			   pstmt.setString(1, id);
			   pstmt.setString(2, "0009");
			   pstmt.setDate(3, d);
			   pstmt.setString(4, "Upgrade phone");
			   pstmt.setString(5,cell);
			   
			   result = pstmt.executeUpdate();
			   
			   
		   }
		   else {
			   System.out.println("Cellphone not available");
		   }
		   
	   }catch(IOException e){
		   e.printStackTrace();
	   }catch(SQLException e){
		   e.printStackTrace();
	   }catch(ParseException e) {
		   e.printStackTrace();
	   }
	   finally {
		   Dbconnection.closeConnection();
	   }
	 
	 return 0;
  }
}