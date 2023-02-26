package com.qa.pages;

import org.openqa.selenium.WebElement;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductDetailsPage extends SidePage {
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"container header\"]/android.widget.TextView")
	private WebElement SLBTitle;
	
	@AndroidFindBy(accessibility = "product description")
	private WebElement SLBDesc;
	
	public String getSLBTitle() {
		return getText(SLBTitle);
	}
	
	public String getSLBDesc() {
		return getText(SLBDesc);
	}
	
	public ProductsPage backToProductsPage() {
		pressSideMenu();
		return pressCatalogMenu();
	}
	  
}
