package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver; // Default value is null

	private ElementUtil eleUtil;

	// 1. We need to add the Private By locators as per the pom framework structure
	// Basically its encapsulation - private methods can be used through public
	// methods .Hence we are creating private by locators

	// If we make the below methods private -anyone can access it and change the
	// password or email ids .Hence we create private and access through public

	// Dont make it static or final

	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginbtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");

	// 2.We will also create a page constructor to initialize the driver
	// We dont want to do unnecessary inheritance with the driverfactory class

	public LoginPage(WebDriver driver) {

		this.driver = driver;

		eleUtil = new ElementUtil(driver);
	}

	// 3. page actions/methods (Should be public methods)

	
	@Step("...getting the login page")
	public String getLoginPageTitle() {

		String title = eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,
				AppConstants.LOGIN_PAGE_TITLE_VALUE);
		System.out.println("Login page title:" + title);
		return title;

	}

	public String getLoginPageUrl() {

		String url = eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,
				AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
		System.out.println("Login page url:" + url);
		return url;

	}

	public boolean isForgotPwdLinkExist() {

		return eleUtil.waitForElementVisible(forgotPwdLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}

	@Step("login with username : {0} and password :{1}")
	public AccountsPage doLogin(String un, String pwd) {

		System.out.println("App credentials are" + un + ":" + pwd);

		eleUtil.waitForElementVisible(emailId, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doclick(loginbtn);
		return new AccountsPage(driver);

	}
	
	public RegisterPage navigateToRegisterPage() {
		
		eleUtil.doclick(registerLink);
		return new RegisterPage(driver);
	}

}
