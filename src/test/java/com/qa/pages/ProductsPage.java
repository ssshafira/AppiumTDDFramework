package com.qa.pages;

import org.openqa.selenium.WebElement;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends SidePage {
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"container header\"]/android.widget.TextView")
	private WebElement productTitle;
	
	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"store item text\"])[1]")
	private WebElement SLBTitle;
	
	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"store item price\"])[1]")
	private WebElement SLBPrice;
	
	public String getTitle() {
		return getText(productTitle);
	}
	
	public String getSLBTitle() {
		return getText(SLBTitle);
	}
	
	public String getSLBPrice() {
		return getText(SLBPrice);
	}
	
	public ProductDetailsPage pressSLBTitle() {
		click(SLBTitle);
		return new ProductDetailsPage();
	}
	  
}
