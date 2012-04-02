package com.telcor.informationprocessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import com.telcor.db.Dbconnection;

public class Customer {
  
  Long cid;
  String ssn;
  String name;
  Date date;
  String gender;
  String phone;
  String address;
  long accountHolder;
  
public Customer() {
	super();
	// TODO Auto-generated constructor stub
}
public Customer(Long cid, String ssn, String name, Date date,
		String gender, String phone, String address) {
	super();
	this.cid = cid;
	this.ssn = ssn;
	this.name = name;
	this.date = date;
	this.gender = gender;
	this.phone = phone;
	this.address = address;
}

public Long getCid() {
	return cid;
}
public void setCid(Long cid) {
	this.cid = cid;
}
public long getAccountHolder() {
	return accountHolder;
}
public void setAccountHolder(long l) {
	this.accountHolder = l;
}
public String getSsn() {
	return ssn;
}
public void setSsn(String ssn) {
	this.ssn = ssn;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
   protected int insertCustomer() {
	   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   Customer c= new Customer();
	   try {
		   DateFormat formatter;
		   formatter = new SimpleDateFormat("dd-MMM-yyyy");
		   //System.out.println("Enter Customer id:");
		   //c.setCid(br.readLine());
		   System.out.println("Enter SSN:");
		   c.setSsn(br.readLine());
		   System.out.println("Enter Name:");
		   c.setName(br.readLine());
		   System.out.println("Enter DOB(dd-mmm-yyyy):");
		   c.setDate(new java.sql.Date((formatter.parse(br.readLine())).getTime()));  
		   System.out.println("Enter Gender:");
		   c.setGender(br.readLine());
		   System.out.println("Enter Phone Number:");
		   c.setPhone(br.readLine());
		   System.out.println("Enter Address:");
		   c.setAddress(br.readLine());
		   System.out.println("Enter Account holder (\"0\" if prepaid plan, family plan \"0\" or ownerid if family plan):");
		   c.setAccountHolder(Long.parseLong(br.readLine()));
		   
	   }catch(IOException e){
		   e.printStackTrace();
	   }catch(ParseException e){
		   e.printStackTrace();
	   }
	   Connection conn = Dbconnection.getConnection();
	   Statement stmt = null;
	   PreparedStatement pstmt = null;
	   try {
		   stmt = conn.createStatement();
		   //Used sequence
		   pstmt = conn.prepareStatement("INSERT INTO CUSTOMER VALUES(customer_seq.NEXTVAL,?,?,?,?,?,?,?)");
		   
		   //pstmt.setString(1, c.getCid());
		   pstmt.setString(1, c.getSsn());
		   pstmt.setString(2, c.getName());
		   pstmt.setDate(3, c.getDate());
		   pstmt.setString(4, c.getGender());
		   pstmt.setString(5, c.getPhone());
		   pstmt.setString(6, c.getAddress());
		   pstmt.setLong(7, c.getAccountHolder());
		   
		   int result = pstmt.executeUpdate();
		   Dbconnection.closeConnection();
		   return result;
	   }catch(SQLException e){
		   e.printStackTrace();
	   }finally {
		   Dbconnection.closeConnection();
	   }
	   return 0;
   }
   private int updateCustomer(long custid) {
	   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   Customer c= new Customer();
	   System.out.println("Enter the Updation values");
	   try {
		   DateFormat formatter;
		   formatter = new SimpleDateFormat("dd-MMM-yyyy");
		   System.out.println("Enter SSN:");
		   c.setSsn(br.readLine());
		   System.out.println("Enter Name:");
		   c.setName(br.readLine());
		   System.out.println("Enter DOB(dd-mmm-yyyy):");
		   c.setDate(new java.sql.Date((formatter.parse(br.readLine())).getTime()));  
		   System.out.println("Enter Gender:");
		   c.setGender(br.readLine());
		   System.out.println("Enter Phone Number:");
		   c.setPhone(br.readLine());
		   System.out.println("Enter Adress:");
		   c.setAddress(br.readLine());
		   
	   }catch(IOException e){
		   e.printStackTrace();
	   }catch(ParseException e){
		   e.printStackTrace();
	   }
	   Connection conn = Dbconnection.getConnection();
	   Statement stmt = null;
	   PreparedStatement pstmt = null;
	   try {
		   stmt = conn.createStatement();
		   pstmt = conn.prepareStatement("UPDATE CUSTOMER SET ssn=? , name=?, dob=? , gender=? , phone_number=? , address=?  WHERE cid= ? ");
		   
		   pstmt.setString(1, c.getSsn());
		   pstmt.setString(2, c.getName());
		   pstmt.setDate(3, c.getDate());
		   pstmt.setString(4, c.getGender());
		   pstmt.setString(5, c.getPhone());
		   pstmt.setString(6, c.getAddress());
		   pstmt.setLong(7, custid);
		   
		   int result = pstmt.executeUpdate();
		   Dbconnection.closeConnection();
		   return result;
	   }catch(SQLException e){
		   e.printStackTrace();
	   }finally {
		   Dbconnection.closeConnection();
	   }
	   return 0;
   }
   private int deleteCustomer(long custid){
	   Connection conn = Dbconnection.getConnection();
	   Statement stmt = null;
	   PreparedStatement pstmt = null;
	   try {
		   stmt = conn.createStatement();
		   pstmt = conn.prepareStatement("DELETE FROM CUSTOMER WHERE cid=?");
		
		   pstmt.setLong(1, custid);
		   int rs = pstmt.executeUpdate();
		   return rs;
	   }catch(SQLException e){
		   e.printStackTrace();
	   }finally{
			Dbconnection.closeConnection();
			
		}
	  return 0;
   }
   private boolean findCustomer(long custid){
	  
	   PreparedStatement pstmt = null;
	   try {
		   Connection conn = Dbconnection.getConnection();
		   pstmt = conn.prepareStatement("SELECT cid FROM CUSTOMER WHERE CID=?");
		   pstmt.setLong(1, custid);
		   ResultSet rs = pstmt.executeQuery();
	       if(rs.next()){
	   
	    	   return true;
	       }
	       else {
	    	   return false;
	       }
	   }catch(SQLException e){
		   e.printStackTrace();
		   return false;
	   }finally{
		   Dbconnection.closeConnection();
	   }
  } 
   public static void getMenu(){
	   int selectOption = 0;
		String opt=null;
		//Buffered reader to read the input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("\n");
		System.out.println("Customer Menu");
		System.out.println("---------------------------");
		while(selectOption!=4){
			System.out.println("Select an option");
			System.out.println("----------------");		
			
			System.out.println("1. Add Customer");
			System.out.println("2. Update Customer");
			System.out.println("3. Delete Customer");
			System.out.println("4. Exit");
			try {
				opt=br.readLine();
				selectOption = Integer.parseInt(opt);
				
				switch(selectOption){
				case 1:
					int r= new Customer().insertCustomer();
					if(r==1)
						System.out.println("Successfully inserted");
					else
						System.out.println("Insertion Failed");
					break;
				case 2: 
					System.out.println("Enter Customer id ");
					long custid = Long.parseLong(br.readLine());
					boolean b=  new Customer().findCustomer(custid);
					if(b){
						
						int r1= new Customer().updateCustomer(custid);
						if(r1==1)
							System.out.println("Update Successful");
						else
							System.out.println("Update Unsuccessful");
						
					}
					
					break;
				case 3: 
					System.out.println("Enter Customer id: ");
					long custid1 = Long.parseLong(br.readLine());
					boolean b1 = new Customer().findCustomer(custid1);
					if(b1){
						int r2= new Customer().deleteCustomer(custid1);
						if(r2==1)
							System.out.println("Delete Successful");
						else 
							System.out.println("Delete Unsuccessful");
					}
					break;
				case 4: break;
				default : 
					System.out.println("Invalid option");
					break;
			 }
				
			}catch(IOException e){
				e.printStackTrace();
			}
			
        }

  }
}
