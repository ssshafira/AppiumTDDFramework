package com.qa.pages;

import org.openqa.selenium.WebElement;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends SidePage {
	
	@AndroidFindBy(accessibility = "Username input field")
	private WebElement usernameTxtFld;
	
	@AndroidFindBy(accessibility = "Password input field")
	private WebElement passwordTxtFld;
	
	@AndroidFindBy(accessibility = "Login button")
	private WebElement loginBtn;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"generic-error-message\"]/android.widget.TextView")
	private WebElement errTxt;
	
	public LoginPage enterUsername(String username) {
		utils.log().info("Login with username : " + username);
		sendKeys(usernameTxtFld, username);
		return this;
	}
	
	public LoginPage enterPassword(String password) {
		utils.log().info("Login with password : " + password);
		sendKeys(passwordTxtFld, password);
		return this;
	}
	
	public ProductsPage pressLoginBtn() {
		utils.log().info("Click login button");
		click(loginBtn);
		return new ProductsPage();
	}
	
	public ProductsPage login(String username, String password) {
		utils.log().info("Do login");
		enterUsername(username);
		enterPassword(password);
		return pressLoginBtn();
	}
	
	public String getErrTxt() {
		utils.log().info("Error message : " + getText(errTxt));
		return getText(errTxt);
	}
	  
}
