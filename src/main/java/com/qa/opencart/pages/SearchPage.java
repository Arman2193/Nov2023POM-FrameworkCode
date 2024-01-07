package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By searchProductResults = By.cssSelector("div#content div.product-layout");

	public SearchPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public int getSearchProductCounts() {

		int productCount= eleUtil.waitForElementsVisible(searchProductResults, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("Product count:::" + productCount);
		return productCount;
	}

	public ProductInfoPage selectProduct(String ProductName) {
		By prodLoctaor = By.linkText(ProductName);
		eleUtil.waitForElementVisible(prodLoctaor,AppConstants.DEFAULT_MEDIUM_TIME_OUT).click();

		return new ProductInfoPage(driver);

	}

}
