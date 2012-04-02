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

public class Warehouse {
   String phone;
   String mid;
   int quantity;
public Warehouse() {
	super();
	// TODO Auto-generated constructor stub
}
public Warehouse(String phone, String mid, int quantity) {
	super();
	this.phone = phone;
	this.mid = mid;
	this.quantity = quantity;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getMid() {
	return mid;
}
public void setMid(String mid) {
	this.mid = mid;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}

public static boolean isCellphoneAvailable(String cellphoneName){
	
	ResultSet rs = null;
	String sql = "SELECT QUANTITY FROM WAREHOUSE WHERE QUANTITY>0 AND CELLPHONENAME = ?";
	
	Connection conn = Dbconnection.getConnection();
	PreparedStatement stmt;
	try {
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, cellphoneName);
		rs = stmt.executeQuery();
		
		if(rs.next())
			return true;
		else
			return false;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dbconnection.closeConnection();
	}
	
	return false;
}

public static boolean decreaseCellphoneQuantity(String cellphoneName){
	
	int rs = 0;
	String sql = "UPDATE WAREHOUSE SET QUANTITY = QUANTITY-1 WHERE CELLPHONENAME = ?";
	
	Connection conn = Dbconnection.getConnection();
	PreparedStatement stmt;
	try {
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, cellphoneName);
		rs = stmt.executeUpdate();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		
		Dbconnection.closeConnection();
	}
	
	return false;
}

  private int insertWarehouse(){
	  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	  Warehouse w = new Warehouse();
	  try {
		  System.out.println("Enter cellphone name:");
		  w.setPhone(br.readLine());
		  System.out.println("Enter manufacurer id:");
		  w.setMid(br.readLine());
		  System.out.println("Enter quantity:");
		  w.setQuantity(Integer.parseInt(br.readLine()));
		  
	  }catch(IOException e){
		  e.printStackTrace();
	  }
	   Connection conn = Dbconnection.getConnection();
	   Statement stmt = null;
	   PreparedStatement pstmt = null;
	   try {
		   stmt = conn.createStatement();
		   pstmt = conn.prepareStatement("INSERT INTO WAREHOUSE VALUES(?,?,?)");
		   
		   pstmt.setString(1, w.getPhone());
		   pstmt.setString(2, w.getMid());
		   pstmt.setInt(3, w.getQuantity());
		  
		   int result = pstmt.executeUpdate();
		   return result;
		   
	   }catch(SQLException e){
		   e.printStackTrace();
		   
	   }finally{
		   Dbconnection.closeConnection();
	   }
	   
  return 0;		   
	
  }
  private int updateWarehouse(String cell,String mid){
	  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	  Warehouse w = new Warehouse();
	  try {
		
		  System.out.println("Enter quantity:");
		  w.setQuantity(Integer.parseInt(br.readLine()));
		  
	  }catch(IOException e){
		  e.printStackTrace();
	  }
	   Connection conn = Dbconnection.getConnection();
	   Statement stmt = null;
	   PreparedStatement pstmt = null;
	   try {
		   stmt = conn.createStatement();
		   pstmt = conn.prepareStatement("UPDATE WAREHOUSE SET quantity=? WHERE CELLPHONENAME=? AND MANUFACTURERID=?");
		   
		 
		   pstmt.setInt(1, w.getQuantity());
		   pstmt.setString(2, cell);
		   pstmt.setString(3, mid);
		  
		   int result = pstmt.executeUpdate();
		   return result;
		   
	   }catch(SQLException e){
		   e.printStackTrace();
		   
	   }finally{
		   Dbconnection.closeConnection();
	   }
	   
  return 0;		   
  }
  private int deleteWarehouse(String cell,String mid){
	  Connection conn = Dbconnection.getConnection();
	   Statement stmt = null;
	   PreparedStatement pstmt = null;
	   try {
		   stmt = conn.createStatement();
		   pstmt = conn.prepareStatement("DELETE FROM WAREHOUSE WHERE CELLPHONENAME=? AND MANUFACTURERID=?");   
		   pstmt.setString(1, cell);
		   pstmt.setString(2, mid);
		   int rs = pstmt.executeUpdate();
		   return rs;
	   }catch(SQLException e){
		   e.printStackTrace();
	   }finally{
			Dbconnection.closeConnection();
			
		}
	  return 0;
  }
  private boolean findInWarehouse(String cell,String mid){
	  PreparedStatement pstmt = null;
	   try {
		   Connection conn = Dbconnection.getConnection();
		   pstmt = conn.prepareStatement("SELECT CELLPHONENAME FROM WAREHOUSE WHERE CELLPHONENAME=? AND MANUFACTURERID=?");
		   pstmt.setString(1, cell);
		   pstmt.setString(2, mid);
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
  public static void getMenu() {
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
			
			System.out.println("1. Add Warehouse");
			System.out.println("2. Update Warehouse");
			System.out.println("3. Delete Warehouse");
			System.out.println("4. Exit");
			try {
				opt=br.readLine();
				selectOption = Integer.parseInt(opt);
				
				switch(selectOption){
				case 1:
					int r= new Warehouse().insertWarehouse();
					if(r==1)
						System.out.println("Successfully inserted");
					else
						System.out.println("Insertion Failed");
					break;
					
				case 2:
					System.out.println("Enter Cellphone name: ");
					String cell = br.readLine();
					System.out.println("Enter Manufacture id:");
					String id = br.readLine();
					boolean b=  new Warehouse().findInWarehouse(cell,id);
					if(b){
						
						int r1= new Warehouse().updateWarehouse(cell,id);
						if(r1==1)
							System.out.println("Update Successful");
						else
							System.out.println("Update Unsuccessful");
						
					}
					
					
					break;
				case 3:
					System.out.println("Enter Cellphone name: ");
					String cell1 = br.readLine();
					System.out.println("Enter Manufacturer id:");
					String  id1 = br.readLine();
			
					boolean b1 = new Warehouse().findInWarehouse(cell1,id1);
					if(b1){
						int r2= new Warehouse().deleteWarehouse(cell1,id1);
						if(r2==1)
							System.out.println("Delete Successful");
						else 
							System.out.println("Delete Unsuccessful");
					}
					break;
				case 4:
			        
					break;
				default : 
					System.out.println("Invalid Option");
					break;
				}
         }catch(IOException e){
        	 e.printStackTrace();
         }
	}
  }        
}
