package com.canvas.qa.pages.profile;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

public class MyAccountPage extends BasePage

{

	@FindBy(xpath = "//ul[@id = 'side-menu']/li[contains(., 'Account')]")
	WebElement account;

	WebDriver driver;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(linkText = "Users")
	WebElement manageUserPage;

	@FindBy(linkText = "Profile")
	WebElement profile;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public MyAccountPage(WebDriver driver) {
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
		clickOnHiddenElement(account, driver);
		// account.click();
	}

	public void logout() {
		customWait(6);
		logout.click();
	}

	public boolean manageUserIsDisplayed() {
		return manageUserPage.isDisplayed();
	}

	/**
	 * Method for Click action on Try Canvas Free Button on First Page.
	 * 
	 * @throws InterruptedException
	 */
	public MyAccountPage profileBtnClick() {
		clickOnHiddenElement(profile, driver);
		// (profile).click();
		return this;
	}
}
