package testcases;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import DataUtil.dataprovider;
import DataUtil.properties;


public class MakeMyTrip extends properties{

	public static WebDriver driver;
	static Date d1,d2;
	static List<WebElement> DepFilghtCount,ArrFilghtCount;
	
	@BeforeTest
	public void intialization() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver","/Users/pavanivemula/Documents/Drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.makemytrip.com/");
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//li[@data-cy='account']")).click();
		Thread.sleep(2000);
	        }
	@Test(priority =1)
	public static void Flight() throws InterruptedException {
		driver.findElement(By.xpath("//ul[@class='fswTabs latoBlack greyText']/li[2]")).click();
    	driver.findElement(By.xpath("//div[@class='fsw_inputBox searchCity inactiveWidget ']")).click();
        driver.findElement(By.xpath("//input[@placeholder='From']")).sendKeys("DEL");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[text()='DEL']")).click();
    	
    	driver.findElement(By.cssSelector("div.fsw_inputBox.searchToCity.inactiveWidget ")).click(); 
    	driver.findElement(By.xpath("//input[@placeholder='To']")).sendKeys("Bengaluru");
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//div[text()='BLR']")).click(); 
	}
	@Test(priority =2,dependsOnMethods="Flight")
	public void DataPicker() throws ParseException, InterruptedException {
		init_prop();
    	SimpleDateFormat FormatDate = new SimpleDateFormat("MMM dd");
    	String DepartureDt =prop.getProperty("DepartureDate");
    	String ReturnDt =prop.getProperty("ReturnDate");
    	 d1 = FormatDate.parse(DepartureDt);
    	 d2 = FormatDate.parse(ReturnDt);
    	System.out.println("The departure date is ... " + FormatDate.format(d1));
        System.out.println("The Return date is ... " + FormatDate.format(d2));
        
    	if(Differences(d1,d2)>=7) {
    	driver.findElement(By.xpath("//div[contains(@aria-label,'"+DepartureDt+"')]")).click();
    	driver.findElement(By.xpath("//div[contains(@aria-label,'"+ReturnDt+"')]")).click();

        }else {
          System.out.println("This Method is restericated to accept 7 days difference between"
                             + " departure and Arrival date. Please check Given Date again. "
                              + "!!Contact Git Admin for more info");

        }

	}
   
	@Test(priority =3,dependsOnMethods="DataPicker")
	public static void DepartureFlightInfo() throws InterruptedException {
		driver.findElement(By.cssSelector("a.primaryBtn.font24.latoBold.widgetSearchBtn")).click();
    	driver.manage().deleteAllCookies();
    	Thread.sleep(6000);
    	
    	ScrollDown();
	    DepFilghtCount = driver.findElements(By.xpath("//div[@class='splitVw']/child::div[1]/child::div[2]/div/div/label"));
        System.out.println("No.of Departure Flights avialable ..."+DepFilghtCount.size());
        
	}
	
	@Test(priority =4,dependsOnMethods="DepartureFlightInfo")
	public void ReturnFilghtInfo() {
		 ArrFilghtCount = driver.findElements(By.xpath("//div[@class='splitVw']/child::div[2]/child::div[2]/div/div/label"));
	     System.out.println("No.of Return Flights avialable ..."+ArrFilghtCount.size());
	           
	}
	//@Test(priority = 5,dependsOnMethods="ReturnFilghtInfo")
	public static void One_Stop() throws InterruptedException{
		//1-stop
		ScrollUp();
        System.out.println("********  One-Stop option is selected  *********");
        driver.findElement(By.xpath("//p[contains(text(),'New Delhi')]/parent::div/child::div//span[text()='1 Stop']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//p[contains(text(),'Bengaluru')]/parent::div/child::div//span[text()='1 Stop']")).click();
        ScrollDown();
        DepFilghtCount = driver.findElements(By.xpath("//div[@class='splitVw']/child::div[1]/child::div[2]/div/div/label"));
        System.out.println("No.of Departure Flights avialable ....."+ DepFilghtCount.size());
        
	    ArrFilghtCount  = driver.findElements(By.xpath("//div[@class='splitVw']/child::div[2]/child::div[2]/div/div/label"));
        System.out.println("No.of Return Flights avialable ....."+ ArrFilghtCount.size());
        
	                }
	
	public void Non_Stop() throws InterruptedException {
		System.out.println("********** Non-Stop option is selected **********");
        //non-stop
		ScrollUp();
        driver.findElement(By.xpath("//p[contains(text(),'New Delhi')]/parent::div/child::div//span[text()='Non Stop']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//p[contains(text(),'Bengaluru')]/parent::div/child::div//span[text()='Non Stop']")).click();
        
        ScrollDown();
        DepFilghtCount = driver.findElements(By.xpath("//div[@class='splitVw']/child::div[1]/child::div[2]/div/div/label"));
        System.out.println("No.of Departure Flights avialable ....."+ DepFilghtCount.size());
        
	    ArrFilghtCount  = driver.findElements(By.xpath("//div[@class='splitVw']/child::div[2]/child::div[2]/div/div/label"));
        System.out.println("No.of Return Flights avialable ....."+ ArrFilghtCount.size());
      
	          }
   @Test(priority=5,dependsOnMethods ="ReturnFilghtInfo", dataProviderClass = dataprovider.class, dataProvider = "getData")
	
   public static void FarePriceCheck(int depaInx, int ArrivalIdx) throws InterruptedException {
	  
		List<WebElement> Radiobutton_Left = driver.findElements(By.xpath(""
		        + "//div[@class='splitVw']/child::div[1]/child::div[2]"
		        + "/div/div/label/descendant::span[@class='customRadioBtn']"));
		
		ScrollUp();
		ElementVisible();
		if(depaInx > 6 && depaInx <= 10) {
			ElementVisible();
		             }else if(depaInx == 1 && depaInx >=3){
		            	 ElementVisible1();  	 
		                  }
		Radiobutton_Left.get(depaInx).click();
		Thread.sleep(2000);
		        
        List<WebElement> Price_Left = driver.findElements(By.xpath(""
        		+ "//div[@class='splitVw']/child::div[1]/child::div[2]/div/div"
        		+ "/label/descendant::div[@class='makeFlex column relative']/p"));
        String Departure_Fare = driver.findElement(By.xpath("//div[@class='splitviewSticky makeFlex']//child::div[1]"
        		                                 + "/p[contains(text(),'Departure')]"
        		                                 + " //parent::div//following-sibling::div/span")).getText();
        if(Price_Left.get(depaInx).getText().equalsIgnoreCase(Departure_Fare)) {
        	System.out.println("Prices of Departureflight's are Equal ....");
                      }
        
         if(ArrivalIdx > 6 && ArrivalIdx <= 10) {
			   ElementVisible();
		                }else if(ArrivalIdx ==1 && ArrivalIdx >=3){
			            	 ElementVisible1();  	 
		                  }
       
        List<WebElement> Radiobutton_Right = driver.findElements(By.xpath(""
                + "//div[@class='splitVw']/child::div[2]/child::div[2]"
                + "/div/div/label/descendant::span[@class='customRadioBtn']"));
              
        Radiobutton_Right.get(ArrivalIdx).click();
	    Thread.sleep(2000);
	    List<WebElement> Price_Right = driver.findElements(By.xpath("//div[@class='splitVw']/child::div[2]/child::div[2]/div/div/"
	    		+ "label/descendant::div[@class='makeFlex column relative']/p"));
	    String Return_Fare = driver.findElement(By.xpath("//div[@class='splitviewSticky makeFlex']//child::div[2]"
	    		+ "/p[contains(text(),'Return')]//parent::div//following-sibling::div/span")).getText();
	    if(Price_Right.get(ArrivalIdx).getText().equalsIgnoreCase(Return_Fare)) {
        	System.out.println("Prices of Returnflight's are Equal ....");
                      }
	}
	@AfterTest
    public void quit() {
		//driver.quit();
	}
	
	
public static int Differences(Date one, Date two) {
		
		int difference = (int) (two.getTime() - one.getTime());
	    int daysBetween = (int) (difference / (1000*60*60*24));
	    System.out.println("Number of Days between dates: "+daysBetween);
		return daysBetween;
		
	                }
   public static void ScrollDown() throws InterruptedException {
   	Actions act = new Actions(driver);
   	WebElement ScrollBar = driver.findElement(By.xpath("//html[@lang='en']"));
		for (int i = 0; i <= 30; i++){
             act.moveToElement(ScrollBar).sendKeys(Keys.PAGE_DOWN).build().perform(); //Page Down
             Thread.sleep(1000);  
		              }
       
       
	                                               }
	public static void ScrollUp() throws InterruptedException {
   	Actions act1 = new Actions(driver);
   	WebElement ScrollBar = driver.findElement(By.xpath("//html[@lang='en']"));
		for (int i = 0; i <= 26; i++){
             act1.moveToElement(ScrollBar).sendKeys(Keys.PAGE_UP).build().perform(); //Page Up
             Thread.sleep(1000);  
   	               }
                                        }
	public static void ElementVisible() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,700)");
	    
	    	                 }
	public static void ElementVisible1() throws InterruptedException {
		Actions act = new Actions(driver);
	   	WebElement ScrollBar = driver.findElement(By.xpath("//html[@lang='en']"));
			for (int i = 0; i <= 2; i++){
	             act.moveToElement(ScrollBar).sendKeys(Keys.PAGE_UP).build().perform(); //Page Up
			}
	}
	public static void ElementScrollIntoView() {
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollBy(0,400)");
		//js1.executeScript("arguments[0].scrollIntoView(true);", ScrollBar);
		         
	}
		
	

}
