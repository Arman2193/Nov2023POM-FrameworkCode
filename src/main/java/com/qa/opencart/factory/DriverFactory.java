package com.qa.opencart.factory;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.hslf.blip.PNG;
import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {

	public WebDriver driver;

	public Properties prop;

	public OptionsManager optionsManager;
	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This method is initializing the driver on the basis of the given browser name
	 * 
	 * @param browserName
	 * @return this returns the driver
	 */

	public WebDriver initDriver(Properties prop) {

		optionsManager = new OptionsManager(prop);

		highlight = prop.getProperty("highlight").trim();

		String browserName = prop.getProperty("browser").toLowerCase().trim();

		System.out.println("browser name is : " + browserName);

		if (browserName.equalsIgnoreCase("chrome")) {

			// driver = new ChromeDriver();
			tlDriver.set(new ChromeDriver());

			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			// above commented code can be run when we need to run in headless mode
		}

		else if (browserName.equalsIgnoreCase("firefox")) {

			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver());
		}

		else if (browserName.equalsIgnoreCase("safari")) {

			// driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
		}

		else if (browserName.equalsIgnoreCase("edge")) {

			// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver());
		} else {

			System.out.println(" plz pass the correct browser... " + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}

	/**
	 * get the local thread copy of the driver
	 * 
	 * 
	 */

	public synchronized static WebDriver getDriver() {
		return tlDriver.get();

	}

	/**
	 * This method is reading the properties file from the .properties file
	 * 
	 * @return
	 */

	public Properties initProp() {
		
		prop = new Properties();
		FileInputStream ip = null;
		
		//mvn clean install -Denv="stage"
		
	String envName= System.getProperty("env");
	System.out.println("Running test cases on Env :" +envName);
	
	try {
	if(envName==null) {
		ip= new FileInputStream("./src/test/resources/config/qa.config.properties");
		System.out.println("no env is passed..... Running tests o QA environment");
		
	}
	
	else {
		
		switch (envName.toLowerCase().trim()) {
		case "qa":
			ip=  new FileInputStream("./src/test/resources/config/qa.config.properties");
			break;

		case "stage":
			ip=  new FileInputStream("./src/test/resources/config/stage.config.properties");
			break;
			
		case "dev":
			ip=  new FileInputStream("./src/test/resources/config/dev.config.properties");
			break;
		case "prod":
			ip=  new FileInputStream("./src/test/resources/config/prod.config.properties");
			break;
			
		default:
			System.out.println("Wrong env is passed...no need to run the test cases....");
			
			throw new FrameworkException("Wrong env passed ");
			//break;
		}	
	}
	}
	catch(FileNotFoundException e) {
		
		
	}
	
		try {
			prop.load(ip);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return prop;

	}
	
	
	

	/**
	 * take the screen shot
	 * 
	 * @return
	 */

	public static String getScreenshot() {

		File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileUtil.copyFile(scrFile, destination);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return path;

	}

}
