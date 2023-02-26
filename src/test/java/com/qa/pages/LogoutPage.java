package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class LogoutPage extends BaseTest {
	
	@AndroidFindBy(id = "android:id/button1")
	private WebElement logoutBtn;
	
	public LoginPage logout() {
		click(logoutBtn);
		click(logoutBtn);
		return new LoginPage();
	}

	  
}
