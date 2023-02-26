package com.qa.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.BaseTest;
import com.qa.pages.SidePage;
import com.qa.pages.LoginPage;
import com.qa.pages.LogoutPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterClass;

public class ProductTests extends BaseTest {
	
		SidePage sidePage;
		LoginPage loginPage;
		LogoutPage logoutPage;
		ProductsPage productsPage;
		ProductDetailsPage productDetailsPage;
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
		}
		
		@BeforeMethod
		public void beforeMethod(Method m) {
			sidePage = new SidePage();
			loginPage = new LoginPage();
			sidePage.pressSideMenu();
			sidePage.pressLoginMenu();
			
			System.out.println("\n ***** Starting Test : " + m.getName() + " ***** \n");
			
			productsPage = loginPage.login(
					  loginUsers.getJSONObject("validUser").getString("username"), 
					  loginUsers.getJSONObject("validUser").getString("password"));
		}
	
		@AfterMethod
		public void afterMethod() {
			productsPage.pressSideMenu();
			logoutPage = sidePage.pressLogoutMenu();
			logoutPage.logout();
		}
	
	  @Test
	  public void validateProductOnProductsPage() {
		  SoftAssert sa = new SoftAssert();
		  
		  String SLBTitle = productsPage.getSLBTitle();
		  sa.assertEquals(SLBTitle, strings.get("slb_title"));
		  
		  String SLBPrice = productsPage.getSLBPrice();
		  sa.assertEquals(SLBPrice, strings.get("slb_price"));

		  sa.assertAll();
	 }
	 
	  @Test
	  public void validateProductOnProductDetailsPage() throws Exception {
		  SoftAssert sa = new SoftAssert();
		  
		  productDetailsPage = productsPage.pressSLBTitle();
		  
		  Thread.sleep(3000);
		  String SLBTitle = productDetailsPage.getSLBTitle();
		  sa.assertEquals(SLBTitle, strings.get("slb_title"));
		  
		  String SLBPrice = productDetailsPage.getSLBDesc();
		  sa.assertEquals(SLBPrice, strings.get("slb_desc"));
		  
		  productsPage = productDetailsPage.backToProductsPage();
		  
		  sa.assertAll();
	  }
  
}
