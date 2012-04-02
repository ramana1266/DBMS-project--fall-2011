package com.telcor.informationprocessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class InformationProcessing {

	public void InformationProcessingMenu(){
		
		int selectOption = 0;
		String opt=null;
		//Buffered reader to read the input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("\n");
		System.out.println("Information Processing menu");
		System.out.println("---------------------------");
		
		while(selectOption!=9){
			System.out.println("Select an option");
			System.out.println("----------------");		
			
			System.out.println("1. Staff Menu");
			System.out.println("2. Customer Menu");
			System.out.println("3. Contract Menu");
			System.out.println("4. Warehouse Menu");
			System.out.println("5. Manufacturer Menu");
			System.out.println("6. Change Rate Plan");
			System.out.println("7. Add a line");
			System.out.println("8. Upgrade Cellphone");
			System.out.println("9. Exit");
			
			try {
				opt=br.readLine();
				selectOption = Integer.parseInt(opt);
				
				switch (selectOption) {
				
				case 1:
					
					Staff.getMenu();
					break;

				case 2:
					new Customer().getMenu();
					break;
					
				case 3:
					new Contract().getMenu();
					break;

				case 4:
					new Warehouse().getMenu();
					break;

				case 5:
					new Manufacturer().GetMenu();
					break;
					
				case 6:
					new Plans().ChangeRatePlan();
					
					break;
				case 7:
					 new Contract().checkNumline();
					 break;
				case 8: 
					 new Contract().upgradeCellphone();
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
