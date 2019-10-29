package com.canvas.qa.pages.submissions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

public class SubmissionAppsPage extends BasePage

{
	String appNameXpath = "//a[text() = '%s']";

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

	@FindBy(id = "common_search_field")
	List<WebElement> searchField;

	@FindBy(xpath = "//i[contains(@class,'fa fa-search text-muted')]")
	WebElement searchIcon;

	@FindBy(linkText = "Submissions")
	WebElement submissionsButton;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public SubmissionAppsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public SubmissionSearchPage clickAppName(String appName) throws InterruptedException {
		if (searchField.size() > 0) {
			searchField.get(0).clear();
			searchField.get(0).sendKeys(appName);
			searchIcon.click();
			//Thread.sleep(5000);
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(String.format(appNameXpath, appName)));
		js.executeScript("arguments[0].click();", fluentWait(element, driver));
		return new SubmissionSearchPage(driver);
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
		//Thread.sleep(5000);
		fluentWait(logout, driver).click();
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
		//Thread.sleep(2000);
		(fluentWait(submissionsButton, driver)).click();
		return this;
	}

}
