package com.canvas.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author priyanka.moral
 *
 */
public class SubmissionAppsPage extends BasePage

{
	@FindBy(xpath = "//table/tbody/tr")
	List<WebElement> appRows;

	@FindBy(css = "table > tbody tr td.date a")
	List<WebElement> apps;

	@FindBy(linkText = "Apps")
	WebElement appsButton;

	WebDriver driver;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(linkText = "Profile")
	WebElement myProfile;

	@FindBy(linkText = "Submissions")
	WebElement submissionsButton;
	
	@FindBy(xpath = "//table[@class='table table-hover table-striped']/tbody/tr/td[2]/a")
	WebElement beerStockRequisitionLink;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public SubmissionAppsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public boolean exportIconExists(String appName) {
		for (WebElement appRow : appRows) {
			if (appRow.getText().contains(appName)) {
				return appRow.findElement(By.className("export-icon")).isDisplayed();
			}
		}

		return false;
	}

	public void logout() throws InterruptedException {
		Thread.sleep(5000);
		logout.click();
	}

	public SubmissionAppsPage submissionClick(String submissionName) throws NoSuchElementException {
		for (WebElement submission : apps) {
			if (submission.getText().contains(submissionName)) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", submission);
				// submission.click();
				return this;
			}
		}

		throw new NoSuchElementException(submissionName + " does not appear for this user.");
	}

	public boolean submissionExist(String submissionName) throws NoSuchElementException {
		for (WebElement submission : apps) {
			if (submission.getText().contains(submissionName)) {
				return true;
			}
		}
		return false;
	}

	public SubmissionAppsPage submissionsButtonClick() throws InterruptedException {
		Thread.sleep(2000);
		(submissionsButton).click();
		return this;
	}
	
	public boolean isSubmissionDisplayed() {
		
		return beerStockRequisitionLink.isDisplayed();
	}
	
	public SubmissionAppsPage clickOnAppLink() {
		beerStockRequisitionLink.click();
		return this;
	}
	
	

}
