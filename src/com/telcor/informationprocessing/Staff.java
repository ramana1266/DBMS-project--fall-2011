package com.telcor.informationprocessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.telcor.db.Dbconnection;

public class Staff {

	String staffId;
	String name;
	int age;
	String gender;
	String phone_number;
	String address;
	String jobTitle;
	String department;
	String prof_title;
	
	public Staff() {
		super();
	}
	
	public Staff(String staffId, String name, int age, String gender,
			String phoneNumber, String address, String jobTitle,
			String department, String profTitle) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.phone_number = phoneNumber;
		this.address = address;
		this.jobTitle = jobTitle;
		this.department = department;
		prof_title = profTitle;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phoneNumber) {
		phone_number = phoneNumber;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getProf_title() {
		return prof_title;
	}
	public void setProf_title(String profTitle) {
		prof_title = profTitle;
	}
	
	private int addStaff(){
		
		//Buffered reader to read the input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Staff s = new Staff();
		
		try{
		
			System.out.print("Enter staff id : ");
			s.setStaffId(br.readLine());
			System.out.print("Enter staff name : ");
			s.setName(br.readLine());
			System.out.print("Enter staff age : ");
			s.setAge(Integer.parseInt(br.readLine()));
			System.out.print("Enter staff gender : ");
			s.setGender(br.readLine());
			System.out.print("Enter staff phone number : ");
			s.setPhone_number(br.readLine());
			System.out.print("Enter staff address : ");
			s.setAddress(br.readLine());
			System.out.print("Enter staff job title : ");
			s.setJobTitle(br.readLine());
			System.out.print("Enter staff department : ");
			s.setDepartment(br.readLine());
			System.out.print("Enter staff professional title : ");
			s.setProf_title(br.readLine());
		
		}catch(IOException e){
			e.printStackTrace();
		}
		
		Connection conn = Dbconnection.getConnection();
		
		String sql = "INSERT INTO STAFF VALUES (\'"+s.staffId+"\',\'"+s.name+"\',"+s.age+",\'"+s.gender+"\',"+s.phone_number+",\'"+s.address+"\',\'"+s.jobTitle+"\',\'"+s.prof_title+"\' )";
		
		try {
			Statement stmt = conn.createStatement();
						
			int result = stmt.executeUpdate(sql);
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			Dbconnection.closeConnection();
		}
			
		return 0;
		
	}
	
	private int updateStaff(String staffId){
		
		//Buffered reader to read the input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Staff s = new Staff();
		
		try{
		
			System.out.print("Enter staff name : ");
			s.setName(br.readLine());
			System.out.print("Enter staff age : ");
			s.setAge(Integer.parseInt(br.readLine()));
			System.out.print("Enter staff gender : ");
			s.setGender(br.readLine());
			System.out.print("Enter staff phone number : ");
			s.setPhone_number(br.readLine());
			System.out.print("Enter staff address : ");
			s.setAddress(br.readLine());
			System.out.print("Enter staff job title : ");
			s.setJobTitle(br.readLine());
			System.out.print("Enter staff department : ");
			s.setDepartment(br.readLine());
			System.out.print("Enter staff professional title : ");
			s.setProf_title(br.readLine());
		
		}catch(IOException e){
			e.printStackTrace();
		}
		
		Connection conn = Dbconnection.getConnection();
		
		String sql = "UPDATE STAFF SET NAME=\'"+s.name+"\'," +
					"AGE="+s.age+",GENDER=\'"+s.gender+"\',PHONE_NUMBER="+s.phone_number+",ADDRESS=\'"+s.address+
					"\',JOB_TITLE=\'"+s.jobTitle+"\',PROFESSIONA_TITLE=\'"+s.prof_title+"\' WHERE STAFFID = \'"+staffId+"\'";
		
		//System.out.println(sql);
		
		try {
			Statement stmt = conn.createStatement();
						
			int result = stmt.executeUpdate(sql);
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Dbconnection.closeConnection();
		}
			
		return 0;
		
	}
	
	private int deleteStaff(String staffId){
				
		Connection conn = Dbconnection.getConnection();
		
		String sql = "DELETE FROM STAFF WHERE STAFFID=\'"+staffId+"'";
		
		try {
			Statement stmt = conn.createStatement();
			
			int rs = stmt.executeUpdate(sql);
				
			return rs;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return 0;
			
		}finally{
			Dbconnection.closeConnection();
			
		}
		
		
	}
	
	private boolean findStaff(String staffID){
		
		
		Connection conn = Dbconnection.getConnection();
		
		String sql = "SELECT STAFFID FROM STAFF WHERE STAFFID=\'"+staffID+"'";
		
		try {
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
				
			if(rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		System.out.println("Staff Menu");
		System.out.println("---------------------------");
		
		while(selectOption!=4){
			System.out.println("Select an option");
			System.out.println("----------------");		
			
			System.out.println("1. Add Staff");
			System.out.println("2. Update Staff");
			System.out.println("3. Delete Staff");
			System.out.println("4. Exit");
			
			try {
				opt=br.readLine();
				selectOption = Integer.parseInt(opt);
				
				switch (selectOption) {
				
				//Code to insert staff
				case 1:
					
					int result = new Staff().addStaff();
					
					if(result == 1)
						System.out.println("Staff inserted successfully\n");
					else
						System.out.println("Staff insertion failed\n");
					break;

				//Code to update staff
				case 2:
					
					System.out.print("Enter staff id : ");
					String staffId = br.readLine();
					
					boolean b =new Staff().findStaff(staffId);
					
					if(b)
					{
						int result1 = new Staff().updateStaff(staffId);
					
						if(result1 == 1)
							System.out.println("Staff updated successfully\n");
						else
							System.out.println("Staff update failed\n");
					}
					else
						System.out.println("Staffid doesn't exist\n");
					
					break;
				
				//Code to Delete staff
				case 3:
					
					System.out.print("Enter staff id : ");
					String staffId1 = br.readLine();
					
					boolean b1 =new Staff().findStaff(staffId1);
					
					if(b1)
					{
						int result1 = new Staff().deleteStaff(staffId1);
					
						if(result1 == 1)
							System.out.println("Staff deleted successfully\n");
						else
							System.out.println("Staff delete failed\n");
					}
					else
						System.out.println("Staffid doesn't exist");					
					
					break;
				
				case 4:
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
