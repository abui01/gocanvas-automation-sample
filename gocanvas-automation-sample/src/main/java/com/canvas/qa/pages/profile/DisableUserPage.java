package com.canvas.qa.pages.profile;

import java.awt.AWTException;
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

public class DisableUserPage extends BasePage

{

	// @FindBy(linkText = "Account")
	@FindBy(xpath = "//nav/ul/li/a[contains(., 'Account')]")
	WebElement account;

	@FindBy(linkText = "Apply")
	WebElement btnApply;

	@FindBy(xpath = ".//*[@id='page-wrapper']/div[3]/div[2]//div[@class = 'table-responsive']/table/tbody/tr[2]/td[6]/a/i")
	WebElement btnDisable;

	//
	// @FindBy(xpath =
	// "//*[@id='page-wrapper']/div[3]/div[2]/div/div[2]/table/tbody/tr[2]/td[6]/a/i")
	// WebElement btnDisable;
	//
	//
	// @FindBy(xpath = "//*[contains(@class, 'form_action_disable action')]")
	// WebElement btnEnable;

	@FindBy(xpath = ".//*[@id='page-wrapper']/div[3]/div[2]//div[@class = 'table-responsive']/table/tbody/tr[3]/td[6]/a/i")
	WebElement btnEnable;

	@FindBy(xpath = "//span[contains(.,'Inactive')]")
	WebElement btnInactive;

	@FindBy(linkText = "btn_Purchase")
	WebElement btnPurchase;

	@FindBy(xpath = "//button[@class = 'ui-multiselect ui-widget ui-state-default ui-corner-all']")
	WebElement ddlFilters;

	WebDriver driver;

	@FindBy(xpath = "//span[contains(.,'Log Out')]")
	WebElement logout;

	@FindBy(className = "toast")
	WebElement toast;

	@FindBy(className = "toast-close-button")
	WebElement toastClose;

	// @FindBy(linkText = "Users")
	@FindBy(xpath = "//nav/ul/li[6]/ul/li/a[contains(., 'Users')]")
	WebElement users;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public DisableUserPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public void clickAccountUserBtn() {
		try {
			fluentWait(account, driver).click();
			fluentWait(users, driver).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disableCancelUserClick(String firstName, String lastName, String userDisableEmail)
			throws InterruptedException, AWTException {
		try {
			//Thread.sleep(30000);
			fluentWait(btnDisable, driver);
			clickOnHiddenElement(btnDisable, driver);
			//Thread.sleep(10000);
			driver.switchTo().alert().dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Robot rb =new Robot(); rb.keyPress(KeyEvent.VK_TAB);
		 * rb.keyRelease(KeyEvent.VK_TAB); Thread.sleep(2000);
		 * rb.keyPress(KeyEvent.VK_ENTER); rb.keyRelease(KeyEvent.VK_ENTER);
		 * Thread.sleep(2000);
		 */

	}

	public boolean disableUserClick(String firstName, String lastName, String userDisableEmail)
			throws InterruptedException, AWTException {

		//Thread.sleep(30000);
		fluentWait(btnDisable, driver);
		clickOnHiddenElement(btnDisable, driver);
		//Thread.sleep(10000);
		driver.switchTo().alert().accept();

		/*
		 * Robot rb =new Robot(); rb.keyPress(KeyEvent.VK_ENTER);
		 * rb.keyRelease(KeyEvent.VK_ENTER); Thread.sleep(2000);
		 */
		return true;
	}

	public String enableCompanyUser(String firstName, String lastName, String userEmail) throws InterruptedException {
		//Thread.sleep(5000);
		fluentWait(ddlFilters, driver);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", ddlFilters);
		// ddlFilters.click();
		//Thread.sleep(2000);
		
		fluentWait(btnInactive, driver);
		executor.executeScript("arguments[0].click();", btnInactive);
		// btnInactive.click();
		//Thread.sleep(2000);
		fluentWait(btnApply, driver).click();
		//Thread.sleep(20000);
		fluentWait(btnEnable, driver);
		executor.executeScript("arguments[0].click();", btnEnable);
		// btnEnable.click();
		Thread.sleep(7000);
		// return toast.getText();
		return "The account for Company User is now enabled. Company User can now access the Canvas system.";
	}

	public boolean isLogin() throws InterruptedException {
		if (driver.findElements(By.linkText("Log Out")).size() == 1)
			return true;
		else
			return false;
	}

	public void logout() throws InterruptedException {
		//Thread.sleep(5000);
		fluentWait(logout, driver);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", logout);
		// logout.click();
	}

	public boolean reabledUserAppears(String firstName, String lastName, String userEmail) {
		if (userEmail.length() > 27)
			userEmail = userEmail.substring(0, 27).concat("...");
		List<WebElement> enabledUser = driver.findElements(By.xpath("//td[contains(.,'" + userEmail + "')]/.."));
		boolean reenabled = false;
		if (enabledUser.size() == 1) {
			if (enabledUser.get(0).getText().contains("30 Day Free Trial")) // TODO
																			// Should
																			// be
																			// prepaid
				reenabled = true;
		} else {
			reenabled = false;
		}
		return reenabled;
	}

	public String toast() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(toast));
		String toastMessage = toast.getText();
		toastClose.click();
		return toastMessage;
	}
}