package com.canvas.qa.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author taukeer.ahmad
 *
 */
public class AriaPage extends BasePage

{
	@FindBy(xpath = "//ul[@id = 'side-menu']/li[contains(., 'Account')]")
	WebElement account;

	@FindBy(linkText = "Billing Info")
	WebElement billingInfo;

	WebDriver driver;

	@FindBy(linkText = "Edit")
	WebElement editbutton;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(linkText = "Profile")
	WebElement profile;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public AriaPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public boolean accountIsDisplayed() {
		try {
			return account.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void clickAccount() {
		fluentWait(account, driver).click();
	}

	/**
	 * Method for Click action on Try Canvas Free Button on First Page.
	 * 
	 * @throws InterruptedException
	 */
	public AriaPage EditFirstName() throws InterruptedException {
		fluentWait(account, driver).click();
		fluentWait(billingInfo, driver).click();
		fluentWait(editbutton, driver).click();
		return this;
	}

	public void logout() throws InterruptedException {

		fluentWait(logout, driver).click();

	}

	public AriaPage Saveeditvalue(String firstname) throws InterruptedException {
		fluentWait(account, driver).click();
		fluentWait(billingInfo, driver).click();
		fluentWait(editbutton, driver).click();
		return this;
	}
}
