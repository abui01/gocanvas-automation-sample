
/**
 * 
 */
package com.canvas.qa.pages.profile;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

public class LoginPage extends BasePage {
	/**
	 * Locators of Login Screen
	 */

	@FindBy(xpath = "//a[contains(.,'Log In')]")  
	WebElement clickonlogin;
	
	@FindBy(xpath = "//a[contains(.,'Log Out')]")  
	List<WebElement> logoutButton;
	@FindBy(id = "hamburger")  
	List<WebElement> topnav;
	
	WebDriver driver;
	@FindBy(xpath = "//input[@type='password']")
	WebElement password;
	@FindBy(xpath = "//button[@id='btn_Log In']")
	WebElement signIn;
	@FindBy(xpath = "//span[contains(.,'Home')]")
	WebElement sucessfullogin;
	// Grabbing the entire side menu
	@FindBy(className = "metismenu")
	WebElement sucessfullogin1;
	@FindBy(xpath = "//input[@id = 'terms_of_use']//parent::label")
	WebElement termsOfUseCheckbox;
	@FindBy(className = "toast")
	WebElement toast;
	@FindBy(xpath = "//*[starts-with(., 'Username and password cannot be blank.')]")
	WebElement unsucessfullogin1;
	@FindBy(xpath = "//*[starts-with(., 'Could not sign')]")
	WebElement unsucessfullogin2;

	@FindBy(xpath = "//input[@name='login']")
	WebElement userName;
	
	@FindBy(xpath = "//span[contains(., 'Search Users')]")
	WebElement clickOnSearchUserLink;
	
	

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	/**
	 * 
	 * Successful Login
	 */
	public String checkForSuccessfulLogin() {
		String successText;
		try {
			
			successText = fluentWait(sucessfullogin, driver).getText();
		} catch (Exception ex) {
			// ex.printStackTrace();
			try {
				successText = fluentWait(sucessfullogin, driver).getText();
			} catch (Exception e) {
				// e.printStackTrace();
				successText = "Failed";
			}
		}
		return successText;

	}

	/**
	 * This checks whether or not a user has successfully logged in
	 * @return
	 */
	public boolean ifSuccessfullLogin() {
		
		int logoutNum = logoutButton.size();
		if (logoutNum>0) {
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 
	 * Successful Login
	 */

	public String checkForSuccessfulLogin1() {
		String successText1;
		try {
			successText1 = fluentWait(sucessfullogin1, driver).getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				successText1 = fluentWait(sucessfullogin1, driver).getText();
			} catch (Exception e) {
				e.printStackTrace();
				successText1 = "Failed";
			}
		}
		return successText1;
	}

	/**
	 * 
	 * Un-Successful Login "Email ID and Password" are Empty
	 */
	public String checkForUnSuccessfulLogin1() {
		String unsuccessText;
		try {
			unsuccessText = fluentWait(unsucessfullogin1, driver).getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				unsuccessText = fluentWait(unsucessfullogin1, driver).getText();
			} catch (Exception e) {
				e.printStackTrace();
				unsuccessText = "Failed";
			}
		}
		return unsuccessText;

	}

	public String checkForUnSuccessfulLogin2() {
		String unsuccessText;
		try {
			unsuccessText = fluentWait(unsucessfullogin2, driver).getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				unsuccessText = fluentWait(unsucessfullogin2, driver).getText();
			} catch (Exception e) {
				e.printStackTrace();
				unsuccessText = "Failed";
			}
		}
		return unsuccessText;

	}

	/**
	 * 
	 * Click on Login button
	 * 
	 */

	public LoginPage Clickonloginbutton() throws InterruptedException {
		// Thread.sleep(15000);
		try {
		//customWait(3);
		clickOnHiddenElement(fluentWait(signIn, driver), driver);
		// JavascriptExecutor executor = (JavascriptExecutor)driver;
		// executor.executeScript("arguments[0].click();", signIn);
		// fluentWait(sucessfullogin, driver); //waits for the "Home" text to
		// appear on following screen
		fluentWait(sucessfullogin, driver);
		/** old wait
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(sucessfullogin));
		//customWait(30);
		 **/

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;

	}

	public OnboardingPage Clickonloginbuttonforfirstlogin() throws InterruptedException {
		clickOnHiddenElement(signIn, driver);
		
		customWait(30);
		return new OnboardingPage(driver);

	}

	public LoginPage Clickonloginbuttonforinvalidlogin() throws InterruptedException {
		clickOnHiddenElement(signIn, driver);

		//customWait(30);
		customWait(10); //modified on 5/15/19
		return this;

	}

	public LoginPage clickTermsOfUseCheckbox() {
		clickOnHiddenElement(termsOfUseCheckbox, driver);
		return this;
	}

	public LoginPage enterCredentialsAndLogin(String userName, String password)
			throws IOException, InterruptedException {
		gotoLogin();
		typeusername(userName);
		typepassword(password);
		Clickonloginbutton();
		return this;
	}
	
	/**
	 * Use this method if page loads a form icon instead of the dashboard
	 * @param userName
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public LoginPage checkForInvalidPageLoad(String userName, String password)
			throws IOException, InterruptedException {
		String url = driver.getCurrentUrl();
		
		/*
		String[] matches = new String[] {"thumb", "asset", "form_icons"};

		for (String s : matches)
		{
		  if (url.contains(s))
		  {
			  	System.out.println("Current invalid URL: " + url);
				System.out.println("DEBUG: Webpage loaded the app thumbnail icon instead of dashboard on user account: " + userName);
				driver.navigate().back();
				typeusername(userName);
				typepassword(password);
				Clickonloginbutton();
		    break;
		  }
		}
		*/
		
		int topNavigation = topnav.size();
		if (topNavigation>0)
		{
			//do nothing
		}
		else {
			System.out.println("Site loaded the form_icon instead of Dashboard. Current URL: " + url);
			driver.navigate().back();
			typeusername(userName);
			typepassword(password);
			Clickonloginbutton();
		}
		
		
		/*
		if (url.contains("thumb"))
		{
			System.out.println("DEBUG: Webpage loaded the app thumbnail icon instead of dashboard on user account: " + userName);
			driver.navigate().back();
			typeusername(userName);
			typepassword(password);
			Clickonloginbutton();
		}
		*/

		return this;
	}

	public String getToastMessage() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(toast));
		return toast.getText();
	}

	public LoginPage gotoLogin() throws IOException, InterruptedException

	{
		// Thread.sleep(3000);
		// WebDriverWait wait = new WebDriverWait(driver, 10);
		// wait.until(ExpectedConditions.elementToBeClickable(clickonlogin));
		clickOnHiddenElement(fluentWait(clickonlogin, driver), driver);

		return this;
	}

	public boolean ifTermsOfUseDispalyed() {

		if (driver.findElements(By.xpath("//input[@id='terms_of_use']")).size() > 0) {
			return true;
		} else
			return false;

	}

	/**
	 * 
	 * Set Password
	 * 
	 */
	public LoginPage typepassword(String pass) throws IOException, InterruptedException

	{
		try {
			clickOnHiddenElement(password, driver);
			//JavascriptExecutor executor = (JavascriptExecutor)driver;
			// executor.executeScript("arguments[0].click();", password);
		    //(password).click();
			(password).clear();

			(password).sendKeys(pass);
			// Thread.sleep(2000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='2px groove red'", password);
			// Thread.sleep(5000);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;

	}

	/**
	 * 
	 * Set User name
	 * 
	 * @throws InterruptedException
	 * 
	 */

	public LoginPage typeusername(String uname) throws IOException, InterruptedException

	{
		// Thread.sleep(10000);
		try {
		fluentWait(userName, driver).click();
		(userName).clear();

		(userName).sendKeys(uname);
		// Thread.sleep(2000);
		/*
		 * JavascriptExecutor js = (JavascriptExecutor) driver;
		 * js.executeScript("arguments[0].style.border='2px groove red'",
		 * userName); CaptureScreenshot.fn_captureScreenshot(driver, "EmailID");
		 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;

	}
	
	public LoginPage clickSearchUser() throws IOException, InterruptedException

	{
		clickOnHiddenElement(fluentWait(clickOnSearchUserLink, driver), driver);
		return this;
	}
}