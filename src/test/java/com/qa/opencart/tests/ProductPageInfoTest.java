package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest {

	@BeforeClass
	public void ProductPageInfoPageSetup() {

		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}

	@DataProvider
	public Object[][] getProductImagesTestData() {

		return new Object[][] {

				{ "Macbook", "MacBook Pro", 4 }, { "iMac", "iMac", 3 }, { "Apple", "Apple Cinema 30\"", 6 },
				{ "Samsung", "Samsung SyncMaster 941BW", 1 },

		};

	}

	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey, String productName, int imagesCount) {

		searchPage = accPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actImagesCount, imagesCount);
	}

	@Test
	public void productInfoTest() {

		searchPage = accPage.performSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		Map<String, String> accProductInfoMap = productInfoPage.getProductInfo();

		softAssert.assertEquals(accProductInfoMap.get("Brand"), "Apple11");
		softAssert.assertEquals(accProductInfoMap.get("Product Code"), "Product 18");

		softAssert.assertEquals(accProductInfoMap.get("productName"), "MacBook Pro");
		softAssert.assertEquals(accProductInfoMap.get("productprice"), "$2,000.00");
		softAssert.assertAll();

	}
	// assert vs verify(soft assertion)

	@Test
	public void addToCartTest() {
		searchPage = accPage.performSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");

		productInfoPage.enterQuantity(2);
		String actCartMsg = productInfoPage.addProductToCart();

		// Success: You have added MacBook Pro to your shopping cart!
		softAssert.assertTrue(actCartMsg.indexOf("Success")>=0);
		softAssert.assertTrue(actCartMsg.indexOf("MacBook Pro")>0);
		softAssert.assertEquals(actCartMsg, "Success: You have added MacBook Pro to your shopping cart!");
		softAssert.assertAll();
	}

}
