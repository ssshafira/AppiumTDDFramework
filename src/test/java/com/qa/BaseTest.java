package com.qa;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
	
	protected static AppiumDriver driver;
	protected static Properties props;
	protected static HashMap<String, String> strings = new HashMap<String, String>();
	protected static String dateTime;
	InputStream inputStream;
	InputStream stringsis;
	protected static TestUtils utils;
	private static AppiumDriverLocalService server;
	
	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@BeforeMethod
	public void beforeMethod() {
		((CanRecordScreen) driver).startRecordingScreen();
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		String media = ((CanRecordScreen) driver).stopRecordingScreen();
		Map<String,String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
		String dir = "videos" + File.separator + params.get("platformName") + "_" +
				params.get("UDID") + File.separator + dateTime + File.separator +
				result.getTestClass().getRealClass().getSimpleName();
		File videoDir = new File(dir);
		
		if(!videoDir.exists()) {
			videoDir.mkdirs();
		}
		
		try {
			FileOutputStream stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
			stream.write(Base64.decodeBase64(media));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  
  @Parameters({"platformName", "UDID"})
  @BeforeTest
  public void beforeTest(String platformName, String UDID) throws Exception {
	  try {
		  utils = new TestUtils();
		  dateTime = utils.getDateTime();
		  props = new Properties();
		  String propFileName = "config.properties";
		  String xmlFileName = "strings/strings.xml";
		  
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		  props.load(inputStream);
		  
		  stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
		  strings = utils.parseStringXML(stringsis);
		  
		  DesiredCapabilities caps = new DesiredCapabilities();
		  caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		  caps.setCapability(MobileCapabilityType.UDID, UDID);
		  caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("androidAutomationName"));
		  caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
		  caps.setCapability("appActivity", props.getProperty("androidAppActivity"));

//		  String appURL = 
//					System.getProperty("user.dir") 
//					+ File.separator + "src" 
//					+ File.separator + "test" 
//					+ File.separator + "resources"
//					+ props.getProperty("androidAppLocation");
//		  System.out.println(appURL);
//		  caps.setCapability(MobileCapabilityType.APP, appURL);
			
		  URL url = new URL(props.getProperty("appiumURL"));
			
		  driver = new AndroidDriver(url, caps);
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	} finally {
		if(inputStream != null) {
			inputStream.close();
		}
		if (stringsis != null) {
			stringsis.close();
		}
	}
  }
  
  @BeforeSuite
  public void beforeSuite() throws Exception {
	  server = getAppiumServerDefault();
	  server.start();
	  server.clearOutPutStreams();
	  System.out.println("Appium Server Started");
  }
  
  @AfterSuite
  public void afterSuite() {
	  server.stop();
	  System.out.println("Appium Server Stopped");
  }
  
  public AppiumDriverLocalService getAppiumServerDefault() {
	  return AppiumDriverLocalService.buildDefaultService();
  }
  
  public AppiumDriver getDriver() {
	  return driver;
  }
  
  public String getDateTime() {
	  return dateTime;
  }
  
  public void waitForVisibility(WebElement e) {
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT));
	  wait.until(ExpectedConditions.visibilityOf(e));
  }
  
  public void waitForStaleness(WebElement e) {
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT));
	  wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(e)));
  }
  
  public void click(WebElement e) {
	  waitForVisibility(e);
	  e.click();
  }
  
  public void clear(WebElement e) {
	  waitForVisibility(e);
	  e.clear();
  }
  
  public void sendKeys(WebElement e, String txt) {
	  clear(e);
	  e.sendKeys(txt);
  }
  
  public String getAttribute(WebElement e, String attribute) {
	  waitForVisibility(e);
	  return e.getAttribute(attribute);
  }
  
  public String getText(WebElement e) {
	  waitForVisibility(e);
	  return e.getAttribute("text");
  }
  
  public void closeApp() {
	  ((InteractsWithApps) driver).terminateApp(props.getProperty("androidAppPackage"));
  }
  
  public void launchApp() {
	  ((InteractsWithApps) driver).activateApp(props.getProperty("androidAppPackage"));
  }

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
