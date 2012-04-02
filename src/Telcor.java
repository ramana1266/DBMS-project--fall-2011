import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.telcor.billingaccount.BillingInfo;
import com.telcor.informationprocessing.InformationProcessing;
import com.telcor.report.Report;
import com.telcor.servicerecordmaintenance.ServiceUsageRecord;


//Main class to start execution
public class Telcor {

	public static void main(String[] args) {
		
		int selectOption = 0;
		String opt=null;
		//Buffered reader to read the input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Main menu");
		System.out.println("---------");
		
		while(selectOption!=5){
			System.out.println("Select an option");
			System.out.println("----------------");		
			
			System.out.println("1. Information processing");
			System.out.println("2. Maintain service usage record");
			System.out.println("3. Maintain billing accounts");
			System.out.println("4. Reports");
			System.out.println("5. Exit");
			
			try {
				opt=br.readLine();
				selectOption = Integer.parseInt(opt);
				
				switch (selectOption) {
				
				case 1:
					
					new InformationProcessing().InformationProcessingMenu();
					break;

				case 2:
					
					new ServiceUsageRecord().serviceUsageRecordMenu();
					break;
					
				case 3:
					BillingInfo.getMenu();
					break;

				case 4:
					
					new Report().reportMenu();
					break;

				case 5:
					System.exit(0);
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
