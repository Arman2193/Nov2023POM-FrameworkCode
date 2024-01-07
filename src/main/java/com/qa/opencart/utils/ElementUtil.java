package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil {

	// Please note - in utility class whenever we use driver , we need to make it
	// private , else it will take the default null value
	// So if we keep it public and try to execute send keys, it will give null
	// pointer exception

	private WebDriver driver;

	public ElementUtil(WebDriver driver) {

		this.driver = driver; // Local to class variable
	}

	public WebElement getElement(By Locator) {

		driver.findElement(Locator);
		return driver.findElement(Locator); // it will return web element.

	}

	public WebElement getElement(By Locator, int timeOut) {
		return waitForElementVisible(Locator, timeOut);

	}

	public List<WebElement> getElements(By locator) {

		return driver.findElements(locator);

	}

	public void doSendKeys(By Locator, String value) {

		getElement(Locator).sendKeys(value);

	}

	// Actions send keys generic method

	public void doActionsSendKeys(By locator, String value) {

		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();

	}

	// Actions click generic method
	public void doActionsClickBy(By locator) {

		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();

	}

	public void doclick(By locator) {

		getElement(locator).click();
	}

	public String doElementGetText(By locator) {
		return getElement(locator).getText();

	}

	public boolean doElementIsDisplayed(By locator) {

		return getElement(locator).isDisplayed();
	}

	public String getElementAtrribute(By locator, String attrName) {

		return getElement(locator).getAttribute(attrName);

	}

	public void getElementAtrributes(By locator, String AttrName) {

		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {

			String attrVal = e.getAttribute(AttrName);
			System.out.println(attrVal);

		}

	}

	public int getTotalElementsCount(By locator) {

		int elecount = getElements(locator).size();
		System.out.println("Total elements for: " + locator + " --------->" + elecount);
		return elecount;
	}

	public List<String> getElementsTextList(By locator) {

		List<String> eleTextList = new ArrayList<String>();
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			eleTextList.add(text);

		}

		return eleTextList;
	}

	// **************************Select based drop down
	// utils***********************//

	// By Index:
	public void doSelectDropDownByIndex(By locator, int index) {

		Select select = new Select(getElement(locator));

		select.selectByIndex(index);

	}

	// By Value:
	public void doSelectDropDownByValue(By locator, String value) {

		Select select = new Select(getElement(locator));

		select.selectByValue(value);

	}

	// By Visible Text:
	public void doSelectDropDownByVisibleText(By locator, String value) {

		Select select = new Select(getElement(locator));

		select.selectByVisibleText(value);

	}

	public List<WebElement> getTotalDropDownOptionsList(By locator) {

		Select select = new Select(getElement(locator));
		return select.getOptions();

	}

	public List<String> getDropdownOptionsTextList(By locator) {

		List<WebElement> optionsList = getTotalDropDownOptionsList(locator);
		List<String> optionsTextList = new ArrayList<String>();

		for (WebElement e : optionsList) {

			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
	}

	// Purpose of the below method is that , if the normal by value , index and
	// visible text id not working -its kind of a back up method .

	public void selectDropDownValue(By locator, String expValue) {

		List<WebElement> optionsList = getTotalDropDownOptionsList(locator);
		for (WebElement e : optionsList) {

			String text = e.getText();
			System.out.println(text);
			if (text.equals(expValue)) {

				e.click();
				break;
			}
		}

	}

	public int getTotalDropDownOptions(By locator) {

		int optionsCount = getTotalDropDownOptionsList(locator).size();
		System.out.println("Total options --->" + optionsCount);
		return optionsCount;
	}

	// Google Search
	public void doSearch(By suggListLocator, String suggName) {

		List<WebElement> suggList = getElements(suggListLocator);

		System.out.println(suggList.size());

		for (WebElement e : suggList) {
			String text = e.getText();

			if (text.length() > 0) {

				System.out.println(text);
			}

			if (text.equals(suggName)) {

				e.click();
				break;

			}
		}
	}

	// ************************* Wait Utils ***************************

	/**
	 * presenceOfElementLocated : An expectation for checking that an element is
	 * present on the DOM of a page and visible. Visibility means that the element
	 * is not only displayed but also has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}

	// * visibility of element located - below is the utility created for it

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	// Will return one element
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 *
	 * @param locator used to find the element
	 * @return the list of WebElements once they are located
	 */

	// Will return list of elements
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 *
	 * @param locator used to find the element
	 * @return the list of WebElements once they are located
	 */

	// Will return list of elements
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

	}

	// Utility - wait for alert
	public Alert waitForAlertPresence(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	// Utility - to get alert text

	public String getAlertText(int timeOut) {
		return waitForAlertPresence(timeOut).getText();
	}

	// Utility - to accept alert text
	public void acceptAlert(int timeOut) {
		waitForAlertPresence(timeOut).accept();
	}

	// Utility - to dismiss alert text
	public void dismissAlert(int timeOut) {
		waitForAlertPresence(timeOut).dismiss();
	}

	// Utility - to send keys
	public void alertSendKeys(int timeOut, String value) {
		waitForAlertPresence(timeOut).sendKeys(value);
	}

	// utility for title contains and fetch- partial title

	public String waitForTitleContainsAndFetch(int timeOut, String titleFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.titleContains(titleFractionValue));
		return driver.getTitle();
	}

	// utility for title is and fetch- exact full title value

	public String waitForTitleIsAndFetch(int timeOut, String titleValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.titleIs(titleValue));
		return driver.getTitle();
	}

	// utility for url contains and fetch details
	public String waitForURLContainsAndFetch(int timeOut, String urlFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.urlContains(urlFractionValue));
		return driver.getCurrentUrl();
	}

	// utility for url contains

	public boolean waitForURLContains(int timeOut, String urlFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.urlContains(urlFractionValue));

	}

	// url to be - after clicking particular webpage to another url
	public String waitForURLIsAndFetch(int timeOut, String urlValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.urlToBe(urlValue));
		return driver.getCurrentUrl();
	}

	// ********* Frame waits**************

	// Will wait for the frame and switch by id or name
	public void waitForFramdAndSwitchToItByIDOrName(int timeOut, String idOrName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}

	// Will wait for the frame and switch by frame index
	public void waitForFramdAndSwitchToItByIndex(int timeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	// Will wait for the frame and switch by frame element
	public void waitForFramdAndSwitchToItByFrameElement(int timeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	// Will wait for the frame and switch by frame locator
	public void waitForFramdAndSwitchToItByFrameLoctor(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 *
	 * @param locator used to find the element
	 * @return the WebElement once it is located and clickable (visible and enabled)
	 */

	// will wait if the clickable element is visible and click on it
	public void clickWhenReady(int timeOut, By Locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(Locator)).click();
	}
	// will wait for the element to be clickable
	public WebElement waitForElementToBeClickable(int timeOut, By Locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.elementToBeClickable(Locator));

	}
	// will wait for the element and click with actions class
	public void doClickWithActionsAndWait(int timeOut, By Locator) {

		WebElement ele = waitForElementToBeClickable(timeOut, Locator);
		Actions act = new Actions(driver);
		act.click(ele).build().perform();
	}

}
