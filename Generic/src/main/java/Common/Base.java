package Common;

import com.thoughtworks.selenium.webdriven.commands.GetAllLinks;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import utility.DataRead;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kashifa on 9/26/16.
 */
public class Base{


    public WebDriver driver = null;

    //    public static Logger logger = LogManager.getLogger(Base.class);
    @Parameters({"useCloudEnv","userName","accessKey","os","browserName","browserVersion","url", "screenCastValue"})
    @BeforeMethod
    public void setUp(@Optional("false") boolean useCloudEnv, @Optional("rahmanww") String userName, @Optional("")
            String accessKey, @Optional("Windows 8") String os, @Optional("firefox") String browserName, @Optional("34")
                              String browserVersion, @Optional("http://www.google.com") String url, @Optional("false") boolean screenCastValue) throws IOException, NullPointerException {
        BasicConfigurator.configure();
        if(useCloudEnv==true){
            //run in cloud
            getCloudDriver(userName,accessKey,os,browserName,browserVersion);
//            logger.setLevel(Level.INFO);
//            logger.info("Test is running on Saucelabs");
        }else{
            //run in local
            getLocalDriver(os, browserName);
//            logger.info("Test is running on Local");
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(url);
        driver.manage().window().maximize();

    }
    public WebDriver getLocalDriver(String os, String browserName){

        if(browserName.equalsIgnoreCase("firefox")){
            driver = new FirefoxDriver();
        }else if(browserName.equalsIgnoreCase("chrome")){
            if(os.equalsIgnoreCase("windows")){
                System.setProperty("webdriver.chrome.driver","..\\Generic\\selenium-browser-driver\\chromedriver.exe");
            }else{
                System.setProperty("webdriver.chrome.driver", "Generic/selenium-browser-driver/chromedriver");
            }
            driver = new ChromeDriver();
        }else if(browserName.equalsIgnoreCase("safari")){
            driver = new SafariDriver();
        }else if(browserName.equalsIgnoreCase("ie")){
            System.setProperty("webdriver.ie.driver","..\\Generic\\selenium-browser-driver\\IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        }else if(browserName.equalsIgnoreCase("htmlunit")){
            //By passing the TRUE parameter, we are enabling JS capabilities.
            driver = new HtmlUnitDriver();
        }else if(browserName.equalsIgnoreCase("phantomJS")) {
            if(os.equalsIgnoreCase("windows")){
                System.setProperty("phantomjs.binary.path","..\\Generic\\selenium-browser-driver\\phantomjs.exe" );
            }
            else {
                System.setProperty("phantomjs.binary.path", "../Generic/selenium-browser-driver/phantomjs");
            }
            driver = new PhantomJSDriver();
        }

        return driver;
    }

    public WebDriver getCloudDriver(String userName,String accessKey,String os, String browserName,
                                    String browserVersion)throws IOException {{

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platform", os);
        cap.setBrowserName(browserName);
        cap.setCapability("version",browserVersion);
        driver = new RemoteWebDriver(new URL("http://"+userName+":"+accessKey+
                "@ondemand.saucelabs.com:80/wd/hub"), cap);
        return driver;
    }
    }
    @AfterMethod
    public void cleanUp(){
        try
        {
            driver.quit();
            //Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
        }
        catch (Exception anException)
        {
            anException.printStackTrace();
        }
//        driver.close();
//        driver.quit();
    }

    public void navigateTo(String url){
        driver.navigate().to(url);
    }




    public void sleepFor(int sec)throws InterruptedException{
        Thread.sleep(sec * 1000);

    }

    //Taking Screen shots on TestoutputData/Screenshots directory
    public void takeScreenshot(String fileName) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("yy-mm-dd HH-mm-ss");
        Date date = new Date();
        FileUtils.copyFile(scrFile, new File("TestoutputData/Screenshots/" + fileName + "_" + dateFormat.format(date) + ".png"));
    }
//
//


    //Synchronization
    public void waitUntilClickAble(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void waitUntilPresenceLocated(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    public void waitUntilVisible(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitUntilVisibleAndClick(By locator){
        waitUntilVisible(locator);
        driver.findElement(locator).click();

    }
    public void waitUntilSelectable(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        boolean element = wait.until(ExpectedConditions.elementToBeSelected(locator));
    }



}
