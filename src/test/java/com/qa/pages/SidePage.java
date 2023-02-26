package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class SidePage extends BaseTest {

	@AndroidFindBy(accessibility = "open menu")
	private WebElement sideMenu;
	
	@AndroidFindBy(accessibility = "menu item log in")
	private WebElement loginMenu;
	
	@AndroidFindBy(accessibility = "menu item log out")
	private WebElement logoutMenu;
	
	@AndroidFindBy(accessibility = "menu item catalog")
	private WebElement catalogMenu;
	
	public SidePage pressSideMenu() {
		click(sideMenu);
		return new SidePage();
	}
	
	public LoginPage pressLoginMenu() {
		click(loginMenu);
		return new LoginPage();
	}
	
	public LogoutPage pressLogoutMenu() {
		click(logoutMenu);
		return new LogoutPage();
	}
	
	public ProductsPage pressCatalogMenu() {
		click(catalogMenu);
		return new ProductsPage();
	}
	
}
