package DataUtil;

import org.testng.annotations.DataProvider;

public class dataprovider {
@DataProvider	
public static Object[][] getData() {
		
		Object[][] data = new Object[5][2];
			
		//{{0,2},{7,5},{1,3},{4,2},{5,3},{6,4}};
		//{10,1},{3,4},{5,6},{8,3},{1,9},{5,3},{2,3}
		
		data[0][0] = 2;
		data[0][1] = 3;
	
		data[1][0] = 5;
		data[1][1] = 6;
		
		data[2][0] = 5;
		data[2][1] = 3;
		
		data[3][0] = 3;
		data[3][1] = 4;
		
		data[4][0] = 10;
		data[4][1] = 1;
		
		/*data[5][0] = 1;
		data[5][1] = 9;
		
		data[6][0] = 8;
		data[6][1] = 2;
		*/
		return data;
         }
}
