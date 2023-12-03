package demo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.checkerframework.checker.units.qual.s;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;


public class TestCases {
    static ChromeDriver driver;
    public TestCases()
    {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    public void endTest()
    {
        System.out.println("End Test: End All TestCases");
        driver.close();
        driver.quit();

    }

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }
    
    static boolean status ;
    public  void testCase01(){
        logStatus("Start TestCase", "TestCase 01 : Verify Make My Trip Homepage URL", "DONE");
        driver.get("https://www.makemytrip.com/");
        status = driver.getCurrentUrl().contains("makemytrip");
        if(status)
            logStatus("TestCase 01", "Test Case pass. The url contains word" , status ? "PASS" : "FAIL");
        else
            logStatus("TestCase 01", "Test Case fail. The url contains word" , status ? "PASS" : "FAIL");

        logStatus("End TestCase", "Test Case pass. The url contains word" , status ? "PASS" : "FAIL");
    }

    // WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));

    public void testCase02() throws InterruptedException
    {
        logStatus("Start TestCase", "TestCase 02 : Get Flight Details from Bangalore to New Delhi", "DONE");
        driver.get("https://www.makemytrip.com/");
        Thread.sleep(20000);
        driver.findElement(By.xpath("//input[@id='fromCity']")).click();
        driver.findElement(By.xpath("//input[@aria-autocomplete='list']")).sendKeys("blr");
        Thread.sleep(2000);
        List<WebElement> list = driver.findElements(By.xpath("//ul[@class='react-autosuggest__suggestions-list']/li"));
        for(WebElement l : list)
        {
            if(l.getText().contains("Bengaluru"))
            {
                l.click();
                break;
            }
        }
        
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='fromCity']")));
        Thread.sleep(2000);
        status = driver.findElement(By.xpath("//input[@id='fromCity']")).getAttribute("value").contains("Bengaluru");
        if(status)
            logStatus("TestCase 02", "Select Bangalore as the departure location" , status ? "PASS" : "FAIL");
        else
            logStatus("TestCase 02", "Test Case Fail. The departure location is not selected as Banglore by sending blr in textfield" , status ? "PASS" : "FAIL");

        driver.findElement(By.xpath("//input[@id='toCity']")).click();
        driver.findElement(By.xpath("//input[@aria-autocomplete='list']")).sendKeys("del");
        List<WebElement> list1 = driver.findElements(By.xpath("//p[@class='font14 appendBottom5 blackText']"));
        for(WebElement l1 : list1)
        {
            if(l1.getText().contains("New Delhi"))
            {
                
                l1.click();
                break;
            }
        }
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='toCity']")));
      
        status = driver.findElement(By.xpath("//input[@id='toCity']")).getAttribute("value").contains("New Delhi");
        if(status)
            logStatus("TestCase 02", "Select New Delhi as the arrival location" , status ? "PASS" : "FAIL");
        else
            logStatus("TestCase 02", "Test Case Fail. The arrival location is not selected as New Delhi by sending del in textfield" , status ? "PASS" : "FAIL");
    
        selectDateForFlight();
       
        status = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']/div")).getText().contains("January2024");
        if(status)
            logStatus("TestCase 02", "selecting January 2024 in the month of travel" , status ? "PASS" : "FAIL");
        else
            logStatus("TestCase 02", "Test Case Fail. The date is not selected as January 2024" , status ? "PASS" : "FAIL");
        
        Thread.sleep(5000);
        driver.findElement(By.xpath("//a[text()='Search']")).click();
        Thread.sleep(5000);
        String flightPrice;
        logStatus("TestCase 02", "Clicking on Search button","DONE");
        try{
             flightPrice = driver.findElement(By.xpath("//div[@class='weeklyFareItems activeDate glider-slide visible left-1']/a/p/following-sibling::p")).getText();
        }
        catch(Exception e)
        {
            flightPrice = driver.findElement(By.xpath("(//div[@class='makeFlex column relative splitfare textRight ']/p)[1]")).getText();
        }
        logStatus("TestCase 02", "Test Case pass. Storing the flight price per adult." , flightPrice);
        
        logStatus("End TestCase", "Test Case pass.Flight Details from Bangalore to New Delhi" , status ? "PASS" : "FAIL");
        
    }

            public static void selectDateForFlight() throws InterruptedException {
            driver.findElement(By.xpath("//div[@class='flt_fsw_inputBox dates inactiveWidget activeWidget']")).click();
            WebElement years = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']/div[1]"));
            while(!years.getText().equals("January2024"))
            {
                driver.findElement(By.xpath("//span[@class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                years = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']/div[1]"));
            }
            List<WebElement> date = driver.findElements(By.xpath("//div[@class='DayPicker-Month']/div[3]/div/div/div/p[1]"));
            driver.findElement(By.xpath("//p[@class='font16 blackText latoBold appendRight30']")).click();
            // Thread.sleep(2000);
            for(WebElement dateToSelect : date)
            {
                if(dateToSelect.getText().equals("20"))
                {

                    dateToSelect.click();
                    Thread.sleep(10000);
                    break;
                }       
            }
        }

        public void testCase03() throws InterruptedException
        {
            logStatus("Start TestCase", "TestCase 03 : Get Train Details from Bangalore to New Delhi", "DONE");
            driver.get("https://www.makemytrip.com/");
            // Thread.sleep(20000);
            logStatus("Start TestCase", "TestCase 03 : Selecting the Train tab", "DONE");
            driver.findElement(By.xpath("//li[@class='menu_Trains']")).click();
            driver.findElement(By.xpath("//div[@class='rsw_inputBox selectRailCity'][1]")).click();
            driver.findElement(By.xpath("//input[@class='react-autosuggest__input react-autosuggest__input--open']")).sendKeys("ypr");
            // Thread.sleep(2000);
            List<WebElement> list = driver.findElements(By.xpath("//ul[@class='react-autosuggest__suggestions-list']/li/div/div/p[1]/span"));
            for(WebElement l : list)
            {
                if(l.getText().contains("Bangalore"))
                {
                    l.click();
                    // Thread.sleep(2000);
                    break;
                }
            }
            status = driver.findElement(By.xpath("//input[@id='fromCity']")).getAttribute("value").contains("Bangalore");
            if(status)
                logStatus("TestCase 03", "Select Bangalore as the departure location" , status ? "PASS" : "FAIL");
            else
                logStatus("TestCase 03", "Test Case Fail. The departure location is not selected as Banglore by sending blr in textfield" , status ? "PASS" : "FAIL");
            // Thread.sleep(2000);
            driver.findElement(By.xpath("//input[@class='react-autosuggest__input react-autosuggest__input--open']")).sendKeys("ndls");
            List<WebElement> list1 = driver.findElements(By.xpath("//ul[@class='react-autosuggest__suggestions-list']/li/div/div/p[1]/span"));
            for(WebElement l1 : list1)
            {
                if(l1.getText().contains("Delhi"))
                {
                    
                    l1.click();
                    break;
                }
            }
            Thread.sleep(2000);
            status = driver.findElement(By.xpath("//input[@id='toCity']")).getAttribute("value").contains("Delhi");
            if(status)
                logStatus("TestCase 03", "Select New Delhi as the arrival location." , status ? "PASS" : "FAIL");
            else
                logStatus("TestCase 03", "Test Case Fail. The arrival location is not selected as New Delhi." , status ? "PASS" : "FAIL");
            
            selectDateForTrain();
            driver.findElement(By.xpath("//li[text()='Third AC']")).click();
            status = driver.findElement(By.xpath("//div[@class='code latoBlack font32 blackText makeRelative']/following-sibling::p")).getText().contains("Third AC");
            if(status)
                logStatus("TestCase 03", "Test Case Pass. Selecting class as 3AC." , status ? "PASS" : "FAIL");
            else
                logStatus("TestCase 03", "Test Case Fail. Selected class is not 3AC." , status ? "PASS" : "FAIL");
            
                driver.findElement(By.xpath("//a[text()='Search']")).click();
            String trainPrice = driver.findElement(By.xpath("//div[@class='ticket-price justify-flex-end']")).getText();
            logStatus("TestCase 03", "Test Case pass. Storing the train price per adult." , trainPrice);
        
            logStatus("End TestCase", "Test Case pass.Train Details from Bangalore to New Delhi" , status ? "PASS" : "FAIL");
           
            
        }
        public static void selectDateForTrain() throws InterruptedException {
            
            WebElement years = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']/div"));
            while(!years.getText().equals("January 2024"))
            {
                driver.findElement(By.xpath("//span[@class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                years = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']/div"));
            }
            List<WebElement> date = driver.findElements(By.xpath("//div[@class='DayPicker-Body']/div/div"));
            for(WebElement dateToSelect : date)
            {
                if(dateToSelect.getText().equals("20"))
                {

                    dateToSelect.click();
                    // Thread.sleep(5000);
                    break;
                }       
            }
            
        }
        public void testCase04() throws InterruptedException
        {
            
            logStatus("Start TestCase", "TestCase 04 : Verify that there are no buses from Bangalore to New Delhi", "DONE");
            driver.get("https://www.makemytrip.com/");
            logStatus("TestCase 04", "Selecting Bus tab", "DONE");
            driver.findElement(By.xpath("//li[@class='menu_Buses']")).click();
            driver.findElement(By.xpath("//label[@class='lbl_input makeFlex column latoBold']")).click();
            driver.findElement(By.xpath("//input[@class='react-autosuggest__input react-autosuggest__input--open']")).sendKeys("bangl");
            // Thread.sleep(2000);
            List<WebElement> list = driver.findElements(By.xpath("//ul[@class='react-autosuggest__suggestions-list']/li/div/p/span"));
            for(WebElement l : list)
            {
                if(l.getText().contains("Bangalore"))
                {
                    l.click();
                    break;
                }
            }
            status = driver.findElement(By.xpath("//input[@id='fromCity']")).getAttribute("value").contains("Bangalore");
            if(status)
                logStatus("TestCase 04", "Select Bangalore as the departure location" , status ? "PASS" : "FAIL");
            else
                logStatus("TestCase 04", "Test Case Fail. The departure location is not selected as Banglore by sending blr in textfield" , status ? "PASS" : "FAIL");
          
            driver.findElement(By.xpath("//input[@class='react-autosuggest__input react-autosuggest__input--open']")).sendKeys("del");
            List<WebElement> list1 = driver.findElements(By.xpath("//ul[@class='react-autosuggest__suggestions-list']/li/div/p/span"));
            for(WebElement l1 : list1)
            {
                if(l1.getText().contains("Delhi"))
                {
                    
                    l1.click();
                    break;
                }
            }
            status = driver.findElement(By.xpath("//input[@id='toCity']")).getAttribute("value").contains("Delhi");
            if(status)
                logStatus("TestCase 04", "Select New Delhi as the arrival location." , status ? "PASS" : "FAIL");
            else
                logStatus("TestCase 04", "Test Case Fail. The arrival location is not selected as New Delhi." , status ? "PASS" : "FAIL");
            selectDateForBus();
            driver.findElement(By.xpath("//button[text()='Search']")).click();
            Thread.sleep(1000);
            status = driver.findElement(By.xpath("//span[text()='No buses found for 20 Jan']")).getText().equals("No buses found for 20 Jan");
            if(status)
                logStatus("TestCase 04", "Verified that message displayed is equals to No buses found for 20 Jan", status ? "PASS" : "FAIL");
            else
                logStatus("TestCase 04", "Not verified that message displayed is equals to No buses found for 20 Jan", status ? "PASS" : "FAIL");
        
            logStatus("End TestCase", "Verified that there are no buses from Bangalore to New Delhi" , status ? "PASS" : "FAIL");
            }

            public static void selectDateForBus() throws InterruptedException {
            WebElement years = driver.findElement(By.xpath("//div[@class='DayPicker-Month']/div/div"));
            while(!years.getText().equals("January 2024"))
            {
                driver.findElement(By.xpath("//span[@class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                years = driver.findElement(By.xpath("//div[@class='DayPicker-Month']/div/div"));
            }
            List<WebElement> date = driver.findElements(By.xpath("//div[@class='DayPicker-Body']/div/div"));
            for(WebElement dateToSelect : date)
            {
                if(dateToSelect.getText().equals("20"))
                {

                    dateToSelect.click();
                    break;
                }       
            }
            
        }


}
