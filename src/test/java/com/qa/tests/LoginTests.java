package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.SidePage;
import com.qa.pages.LoginPage;
import com.qa.pages.LogoutPage;
import com.qa.pages.ProductsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTests extends BaseTest {
	
		SidePage sidePage;
		LoginPage loginPage;
		LogoutPage logoutPage;
		ProductsPage productsPage;
		InputStream datais;
		JSONObject loginUsers;
		
		@BeforeClass
		public void beforeClass() throws Exception {
			try {
				String dataFileName = "data/loginUsers.json";
				datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
				JSONTokener tokener = new JSONTokener(datais);
				loginUsers = new JSONObject(tokener);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (datais != null) {
					datais.close();
				}
			}
			closeApp();
			launchApp();
		}
	
		@AfterClass
		public void afterClass() {
			sidePage = productsPage.pressSideMenu();
			logoutPage = sidePage.pressLogoutMenu();
			loginPage = logoutPage.logout();
		}
		
		@BeforeMethod
		public void beforeMethod() {
			sidePage = new SidePage();
			loginPage = new LoginPage();
			sidePage.pressSideMenu();
			sidePage.pressLoginMenu();
		}
	
		@AfterMethod
		public void afterMethod() {
		}
	
	  @Test
	  public void invalidUsername() {
		loginPage.enterUsername(loginUsers.getJSONObject("invalidUsername").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidUsername").getString("password"));
		loginPage.pressLoginBtn();
		  		  
		String actualErrTxt = loginPage.getErrTxt();
		String expectedErrTxt = strings.get("err_invalid_username_or_password");
		  
		Assert.assertEquals(actualErrTxt, expectedErrTxt);
	  }
	  
	  @Test
	  public void invalidPassword() {
		loginPage.enterUsername(loginUsers.getJSONObject("invalidPassword").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("username"));
		loginPage.pressLoginBtn();
		  		  
		String actualErrTxt = loginPage.getErrTxt();
		String expectedErrTxt = strings.get("err_invalid_username_or_password");
		  
		Assert.assertEquals(actualErrTxt, expectedErrTxt);
	  }
	  
	  @Test
	  public void successfullLogin() throws InterruptedException {
		loginPage.enterUsername(loginUsers.getJSONObject("validUser").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
		productsPage = loginPage.pressLoginBtn();
		  		  
		Thread.sleep(3000);
		
		String actualProductsTitle = productsPage.getTitle();
		String expectedProductsTitle = strings.get("products_title");
		  
		Assert.assertEquals(actualProductsTitle, expectedProductsTitle);
		System.out.println("actual : " + actualProductsTitle);
	  }
  
}
