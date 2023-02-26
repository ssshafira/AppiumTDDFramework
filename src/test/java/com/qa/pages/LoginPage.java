package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest {
	
	@AndroidFindBy(accessibility = "Username input field")
	private WebElement usernameTxtFld;
	
	@AndroidFindBy(accessibility = "Password input field")
	private WebElement passwordTxtFld;
	
	@AndroidFindBy(accessibility = "Login button")
	private WebElement loginBtn;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"generic-error-message\"]/android.widget.TextView")
	private WebElement errTxt;
	
	public LoginPage enterUsername(String username) {
		sendKeys(usernameTxtFld, username);
		return this;
	}
	
	public LoginPage enterPassword(String password) {
		sendKeys(passwordTxtFld, password);
		return this;
	}
	
	public ProductsPage pressLoginBtn() {
		click(loginBtn);
		return new ProductsPage();
	}
	
	public ProductsPage login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		return pressLoginBtn();
	}
	
	public String getErrTxt() {
		return getAttribute(errTxt, "text");
	}
	  
}
