package com.qa.opencart.pages;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	private By productHeader = By.tagName("h1");
	private By productImages= By.cssSelector("ul.thumbnails img");
	private By productMetadata= By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPricedata= By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity= By.id("input-quantity");
	private By addToCartBtn= By.id("button-cart");
	private By cartSuccessMsg= By.cssSelector("div.alert.alert-success");
	
	
	
	
	private Map<String ,String> productInfoMap ;
	public ProductInfoPage(WebDriver driver) {

		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}

	public String getProductHeaderValue() {

		String productHeaderVal = eleUtil.doElementGetText(productHeader);
 
		System.out.println("Product Header " + productHeaderVal);

		return productHeaderVal;
	}
	
	public int getProductImagesCount() {
		
		
	int imagesCount=eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
	System.out.println(" product images count " +imagesCount);
	return imagesCount;
		
	}
	
	
	public void enterQuantity(int qty) {
		
		System.out.println("Product Quantity" +qty);
		eleUtil.doSendKeys(quantity, String.valueOf(qty));
		
		
	}
	
	public String addProductToCart() {
		
		eleUtil.doclick(addToCartBtn);
		String successMsg=	eleUtil.waitForElementVisible(cartSuccessMsg, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		
		//testing
		//testin
		
		StringBuilder sb= new StringBuilder(successMsg);
		String msg=sb.substring(0, successMsg.length()-1).replace("\n", "");
		System.out.println("Cart Success Msg" + msg);
		return msg;
		
	}
	
	public Map<String, String> getProductInfo() {
		
//		Brand: Apple
//		Product Code: Product 18
//		Reward Points: 800
//		Availability: In Stock
	// Will create a a hash map for the above to store the data in key -value pair
		
	//productInfoMap = new HashMap<String , String>();  // Hashmap
		productInfoMap = new LinkedHashMap<String , String>(); // LinkedHashMap
		//productInfoMap = new TreeMap<String , String>(); // tree map
	//header:
	productInfoMap.put("productName", getProductHeaderValue());
	getProductMetaData();
	getProductPriceData();
	System.out.println(productInfoMap);
	
	return productInfoMap;
	
	

	// Price data :
//	$2,000.00
//	Ex Tax: $2,000.00
	
	
	
	}
	
	// fetching metadata
	private void getProductMetaData() {
		
		// Metadata:
		List<WebElement>	metaList=eleUtil.getElements(productMetadata);
		for(WebElement e: metaList) {
			
		String meta=e.getText();//Brand: Apple
		String metaInfo[]=meta.split(":"); 
		String key = metaInfo[0].trim();
		String value = metaInfo[1].trim();
		productInfoMap.put(key, value);
		
		
	}

	}
	
	// fetching price data :
	private void getProductPriceData() {
		
		List<WebElement>priceList=eleUtil.getElements(productPricedata);
		String price=	priceList.get(0).getText()	;
		String exTax=	priceList.get(1).getText()	;
		String extaxVal=exTax.split(":")[1].trim();
			
		productInfoMap.put("productprice", price);
		productInfoMap.put("exTax", extaxVal);
	}
	
}

