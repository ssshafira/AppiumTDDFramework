package com.qa;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class BaseTest {
	
	protected static AppiumDriver driver;
	protected static Properties props;
	protected static HashMap<String, String> strings = new HashMap<String, String>();
	InputStream inputStream;
	InputStream stringsis;
	TestUtils utils;
	
	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
  
  @Parameters({"platformName", "UDID"})
  @BeforeTest
  public void beforeTest(String platformName, String UDID) throws Exception {
	  try {
		  props = new Properties();
		  String propFileName = "config.properties";
		  String xmlFileName = "strings/strings.xml";
		  
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		  props.load(inputStream);
		  
		  stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
		  utils = new TestUtils();
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
