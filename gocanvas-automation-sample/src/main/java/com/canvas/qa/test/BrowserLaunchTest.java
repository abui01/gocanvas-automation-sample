package com.canvas.qa.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.lang3.SystemUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.util.PropertyUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

@Test
public class BrowserLaunchTest extends UtilityFunctions

{
	public static Map<String, HashMap<String, String>> casesToExecute;
	public static WebDriver driver;
	public static ExtentReports extent;
	public static final String REPORT_OUTPUT_FILE = "src/main/resources/reportOutput.csv";
	public static final String SCREENSHOT_DIR = "src/Screenshots";
	public static ExtentTest test;
	public static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>();
	public static final String XLS_FILE = "src/main/resources/Testing.xlsx";
	public static final String YAML_FILE = "src/main/resources/data/default.yml";
	
	@BeforeMethod
	@Parameters({ "testdescription" })
	public final void beforeTests(String testDescription, ITestContext testContext) throws InterruptedException {
		test = extent.startTest(testContext.getName() + ": " + testDescription);
	}

	/***
	 * This method clicks on hidden WebElement using JavascriptExecutor
	 * interface from Selenium
	 * 
	 * @param element
	 */
	public void clickOnHiddenElement(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	@AfterSuite()
	public void close() throws IOException, InterruptedException {
		extent.flush();
		extent.close();
		threadDriver.remove();
	}

	/**
	 * 
	 * Waits for element to load before code execution
	 */
	public WebElement fluentWait(WebElement element) {
		WebElement targetElem = null;
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

			targetElem = wait.until(new Function<WebDriver, WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					return element;
				}
			});
		} catch (Exception ex) {
			org.testng.Assert.assertTrue(false);
		}
		return targetElem;
	}
	
	

	
	public void launchBrowser(String browser) throws ClientProtocolException, IOException {

		String downloadFilepath = System.getProperty("user.dir") + "/src/main/resources/Downloads/";
		browser = PropertyUtils.getProperty("driver.type");
		String osName = System.getProperty("os.name");
		if (osName.contains("Mac")) {
			browser = browser + "_mac";
		}

		if (browser.equalsIgnoreCase("Firefox")) {
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			FirefoxOptions options = new FirefoxOptions();
			options.addPreference("log", "{level: trace}");
			capabilities.setCapability("marionette", true);
			capabilities.setCapability("moz:firefoxOptions", options);
			String driverPath = SystemUtils.IS_OS_WINDOWS ? "driver/geckodriver.exe" : "driver/geckodriver";
			System.setProperty("webdriver.gecko.driver", driverPath);
			driver = new FirefoxDriver(capabilities);
		} else if (browser.equalsIgnoreCase("chrome")) {
			String driverPath = SystemUtils.IS_OS_WINDOWS ? "driver/chromedriver.exe" : "driver/chromedriver";
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			LoggingPreferences logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			HashMap<String, Object> chromePref = new HashMap<>();
			chromePref.put("download.default_directory", downloadFilepath);
			chromePref.put("perfLoggingPrefs", logPrefs);
			options.setExperimentalOption("prefs", chromePref);
			//disableImageChrome(options);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			System.setProperty("webdriver.chrome.driver", driverPath);
			threadDriver.set(new ChromeDriver(cap));
		
			driver = threadDriver.get();

			// driver = new ChromeDriver(options);
			// driver = new ChromeDriver(capabilities);
		} else if (browser.equalsIgnoreCase("chrome_mac")) {
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver_mac");
			threadDriver.set(new ChromeDriver());
			driver = threadDriver.get();
		} else if (browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", "driver/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else if (browser.equalsIgnoreCase("chromeheadless")) {
			String driverPath = SystemUtils.IS_OS_WINDOWS ? "driver/chromedriver.exe" : "driver/chromedriver";
			System.setProperty("webdriver.chrome.driver", driverPath);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			LoggingPreferences logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("headless");
			options.addArguments("no-sandbox");
			options.addArguments("window-size=1920x1080");
			options.addArguments("disable-gpu");
			options.addArguments("test-type");
			options.addArguments("disable-extensions");
			options.addArguments("--proxy-server='direct://'");
			options.addArguments("--proxy-bypass-list=*");
			//disableImageChrome(options);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			ChromeDriverService driverService = ChromeDriverService.createDefaultService();
			ChromeDriver driver2 = new ChromeDriver(driverService, cap);
			Map<String, Object> commandParams = new HashMap<>();
			commandParams.put("cmd", "Page.setDownloadBehavior");
			commandParams.put("download.default_directory", downloadFilepath);
			commandParams.put("download.directory_upgrade", true);
			Map<String, Object> params = new HashMap<>();
			params.put("behavior", "allow");
			params.put("downloadPath", downloadFilepath);
			commandParams.put("params", params);
			ObjectMapper objectMapper = new ObjectMapper();

			HttpClient httpClient = HttpClientBuilder.create().build();
			String command = objectMapper.writeValueAsString(commandParams);
			String u = driverService.getUrl().toString() + "/session/" + (driver2).getSessionId()
					+ "/chromium/send_command";
			HttpPost request = new HttpPost(u);
			request.addHeader("content-type", "application/json");
			request.setEntity(new StringEntity(command));
			httpClient.execute(request);

			threadDriver.set(driver2);
			// threadDriver.set(new ChromeDriver(driverService, options));
			driver = threadDriver.get();
			// driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("firefoxheadless")) {
			FirefoxBinary firefoxBinary = new FirefoxBinary();
			firefoxBinary.addCommandLineOptions("--headless");
			String driverPath = SystemUtils.IS_OS_WINDOWS ? "driver/geckodriver.exe" : "driver/geckodriver";
			System.setProperty("webdriver.gecko.driver", driverPath);
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setBinary(firefoxBinary);
			driver = new FirefoxDriver(firefoxOptions);
		}

		driver.get(PropertyUtils.getProperty("app.url"));
		//driver.manage().window().maximize();
		driver.manage().window().fullscreen();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	/***
	 * This method moves the mouse pointer to the given WebElement
	 * 
	 * @param toElement
	 */
	public void moveMouseToElement(WebElement toElement) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(toElement).build().perform();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	@BeforeSuite
	@Parameters({ "browser" })

	/**
	 * 
	 * Launch the browser
	 */
	public void openBrowser(String browser) {

		extent = new ExtentReports(System.getProperty("user.dir") + "/report/report.html", true);

	}

	/**
	 * @param userName
	 * @param password
	 * @param testStepDescription
	 * @param testContext
	 * @throws IOException
	 * @throws InterruptedException
	 */
	protected void performLogin(int step, String userName, String password, String testStepDescription,
			ITestContext testContext, String testCaseDescription) throws IOException, InterruptedException {
		LoginPage login = new LoginPage(driver);
		login.enterCredentialsAndLogin(userName, password);
		String successText = login.checkForSuccessfulLogin();
		reportLog(true, testContext.getName(), testCaseDescription, "", "STARTED");
		reportLog(successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home"),
				testContext.getName(), testStepDescription, +step + "." + "1", "Sign In successful");

		org.testng.Assert.assertTrue(
				successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home"),
				"SignIn Failure ");
	}

	protected void performLogin(int step, String userName, String password, String testStepDescription,
			ITestContext testContext, String testCaseDescription, String rallyLink)
			throws IOException, InterruptedException {
		LoginPage login = new LoginPage(driver);
		login.enterCredentialsAndLogin(userName, password);
		String successText = login.checkForSuccessfulLogin();
		reportLog(true, testContext.getName(), "Rally Link: ",
				"Rally Link: " + String.format("<a href='%s'>" + rallyLink + "</a>", rallyLink));
		reportLog(successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home"),
				testContext.getName(), testStepDescription, +step + "." + "1", "Sign In successful");

		org.testng.Assert.assertTrue(
				successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home"),
				"SignIn Failure ");
	}

	@AfterMethod
	public void runAfterTest(ITestResult result) throws IOException {
		extent.endTest(test);
	}

	/***
	 * This method verifies if Text is present within the HTML body
	 * 
	 * @param text
	 */
	public boolean verifyTextOnPagePresent(String text) {
		WebElement body = driver.findElement(By.tagName("body"));
		String bodyText = body.getText();
		return bodyText.contains(text);
	}
	
	public WebDriver getDriver(){
		return driver;
	}
	
	public ThreadLocal<WebDriver> getThreadDriver(){
		return threadDriver;
	}
	

	public static void disableImageChrome(ChromeOptions newoptions) {
		HashMap<String, Object> images = new HashMap<String, Object>();
		images.put("images",2);
		HashMap<String, Object> prefs = new HashMap<String, Object>();
		 prefs.put("profile.managed_default_content_settings.images", 2); 
		newoptions.setExperimentalOption("prefs", prefs);
		

	}
}
