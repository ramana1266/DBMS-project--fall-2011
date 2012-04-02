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

public class Manufacturer {
    String mid;
    String phone;
    String address;
    String shipper;
	public Manufacturer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Manufacturer(String mid, String phone, String address, String shipper) {
		super();
		this.mid = mid;
		this.phone = phone;
		this.address = address;
		this.shipper = shipper;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
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
	public String getShipper() {
		return shipper;
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}
	private int insertManufacturer() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Manufacturer m = new Manufacturer();
		try {
			System.out.println("Enter manufacturer id:");
			m.setMid(br.readLine());
			System.out.println("Enter Phone number:");
			m.setPhone(br.readLine());
			System.out.println("Enter Address:");
			m.setAddress(br.readLine());
			System.out.println("Enter Shipper:");
			m.setShipper(br.readLine());
		}catch(IOException e){
			e.printStackTrace();
		}
		   Connection conn = Dbconnection.getConnection();
		   Statement stmt = null;
		   PreparedStatement pstmt = null;
		   try {
			   stmt = conn.createStatement();
			   pstmt = conn.prepareStatement("INSERT INTO MANUFACTURER VALUES(?,?,?,?)");
			   
			   pstmt.setString(1, m.getMid());
			   pstmt.setString(2, m.getPhone());
			   pstmt.setString(3,m.getAddress());
			   pstmt.setString(4, m.getShipper());
			   int result = pstmt.executeUpdate();
			   return result;
			   
		   }catch(SQLException e){
			   e.printStackTrace();
			   
		   }finally{
			   Dbconnection.closeConnection();
		   }
		   
       return 0;		   
	}
	private int updateManufacturer(String id){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Manufacturer m = new Manufacturer();
		try {
			System.out.println("Enter Phone number:");
			m.setPhone(br.readLine());
			System.out.println("Enter Address:");
			m.setAddress(br.readLine());
			System.out.println("Enter Shipper:");
			m.setShipper(br.readLine());
		}catch(IOException e){
			e.printStackTrace();
		}
		   Connection conn = Dbconnection.getConnection();
		   Statement stmt = null;
		   PreparedStatement pstmt = null;
		   try {
			   stmt = conn.createStatement();
			   pstmt = conn.prepareStatement("UPDATE MANUFACTURER SET phone_number=? ,  address=? , shipper=? WHERE manufacturerid=? ");
			   
			   pstmt.setString(1, m.getPhone());
			   pstmt.setString(2,m.getAddress());
			   pstmt.setString(3, m.getShipper());
			   pstmt.setString(4,id);
			   int result = pstmt.executeUpdate();
			   return result;
			   
		   }catch(SQLException e){
			   e.printStackTrace();
			   
		   }finally{
			   Dbconnection.closeConnection();
		   }
		   
       return 0;	
	}
	private int deleteManufacturer(String id){
		   Connection conn = Dbconnection.getConnection();
		   Statement stmt = null;
		   PreparedStatement pstmt = null;
		   try {
			   stmt = conn.createStatement();
			   pstmt = conn.prepareStatement("DELETE FROM MANUFACTURER WHERE MANUFACTURERID=?");   
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
	private boolean findManufacturer(String id) {
		
		 PreparedStatement pstmt = null;
		   try {
			   Connection conn = Dbconnection.getConnection();
			   pstmt = conn.prepareStatement("SELECT MANUFACTURERID FROM MANUFACTURER WHERE MANUFACTURERID=?");
			   pstmt.setString(1, id);
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
    public static void GetMenu() {
    	 int selectOption = 0;
  		String opt=null;
  		//Buffered reader to read the input
  		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  		
  		System.out.println("\n");
  		System.out.println("Manufacturer Menu");
  		System.out.println("---------------------------");
  		while(selectOption!=4){
  			System.out.println("Select an option");
  			System.out.println("----------------");		
  			
  			System.out.println("1. Add Manufacturer");
  			System.out.println("2. Update Manufacturer");
  			System.out.println("3. Delete Manufacturer");
  			System.out.println("4. Exit");
  			try {
  				opt=br.readLine();
  				selectOption = Integer.parseInt(opt);
  				
  				switch(selectOption){
  				case 1:
  					int r= new Manufacturer().insertManufacturer();
					if(r==1)
						System.out.println("Successfully inserted");
					else
						System.out.println("Insertion Failed");
					break;
  							
  				case 2:
  					System.out.println("Enter Manufacturer id ");
					String id = br.readLine();
					boolean b=  new Manufacturer().findManufacturer(id);
					if(b){
						
						int r1= new Manufacturer().updateManufacturer(id);
						if(r1==1)
							System.out.println("Update Successful");
						else
							System.out.println("Update Unsuccessful");
						
					}
					
					break;
  		
  				case 3:
  					System.out.println("Enter Customer id: ");
					String custid1 = br.readLine();
					boolean b1 = new Manufacturer().findManufacturer(custid1);
					if(b1){
						int r2= new Manufacturer().deleteManufacturer(custid1);
						if(r2==1)
							System.out.println("Delete Successful");
						else 
							System.out.println("Delete Unsuccessful");
					}
  					
  					break;
  				case 4:break;
  				}
  			}catch(IOException e){
  				e.printStackTrace();
  			}
  		}
    }
    
}
