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

/**
 * @author priyanka.moral
 *
 */
public class SubmissionsDetailPage extends BasePage

{
	@FindBy(xpath = "//table/tbody/tr")
	List<WebElement> appRows;

	@FindBy(linkText = "Apps")
	WebElement appsButton;

	@FindBy(css = ".breadcrumb a")
	List<WebElement> breadcrumbLinks;

	WebDriver driver;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(linkText = "Profile")
	WebElement myProfile;

	@FindBy(css = ".submission_section > div > p")
	WebElement screenName;

	@FindBy(css = ".submission_section > div dl")
	List<WebElement> submissionDataRows;

	@FindBy(linkText = "Submissions")
	WebElement submissionsButton;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public SubmissionsDetailPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public SubmissionsDetailPage breadcrumbClick(String linkText) throws NoSuchElementException {
		for (WebElement breadcrumbLink : breadcrumbLinks) {
			if (breadcrumbLink.getText().contains(linkText)) {
				breadcrumbLink.click();
				return this;
			}
		}

		throw new NoSuchElementException(linkText + " does not appear for this user.");
	}

	public SubmissionsDetailPage cancelEditSubmissionDataValue(String key, String newValue) {
		for (WebElement submissionData : submissionDataRows) {
			if (submissionData.findElement(By.cssSelector("dt > span")).getText().contains(key)) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();",
						submissionData.findElement(By.cssSelector("dl a[title=\"Edit\"]")));
				// submissionData.findElement(By.cssSelector("dl
				// a[title=\"Edit\"]")).click();
				submissionData.findElement(By.cssSelector("form input[name=\"data\"]")).sendKeys(newValue);
				executor.executeScript("arguments[0].click();",
						submissionData.findElement(By.cssSelector("form a[title=\"Cancel\"]")));
				// submissionData.findElement(By.cssSelector("form
				// a[title=\"Cancel\"]")).click();
			}
		}
		return this;
	}

	public String getScreenName() {
		return screenName.getText();
	}

	public String getSubmissionDataValue(String key) {
		for (WebElement submissionData : submissionDataRows) {
			if (submissionData.findElement(By.cssSelector("dt > span")).getText().contains(key))
				return submissionData.findElement(By.cssSelector("dd > span")).getText();
		}
		return null;
	}

	public boolean isEditable(String field) {
		for (WebElement submissionData : submissionDataRows) {
			return submissionData.findElement(By.cssSelector("dt > span")).getText().contains(field)
					& submissionData.findElements(By.cssSelector("dl a[title=\"Edit\"]")).size() > 0;
		}
		return false;
	}

	public void logout() throws InterruptedException {
		Thread.sleep(5000);
		logout.click();
	}

	public boolean matchSubmissionDataCheckBoxValue(String key, String value) {
		for (WebElement submissionData : submissionDataRows) {
			return submissionData.findElement(By.cssSelector("dt > span")).getText().contains(key)
					& submissionData.findElements(By.cssSelector("dl > span .fa-check-square-o")).size() > 0;
		}
		return false;
	}

	public boolean matchSubmissionDataImage(String key, String value) {
		for (WebElement submissionData : submissionDataRows) {
			return submissionData.findElement(By.cssSelector("dt > span")).getText().contains(key)
					& submissionData.findElements(By.cssSelector("dl > span img")).size() > 0;
		}
		return false;
	}

	public boolean matchSubmissionDataValue(String key, String value) {
		for (WebElement submissionData : submissionDataRows) {
			return submissionData.findElement(By.cssSelector("dt > span")).getText().contains(key)
					& submissionData.findElement(By.cssSelector("dl > span")).getText().contains(value);
		}
		return false;
	}

	public SubmissionsDetailPage submissionsButtonClick() throws InterruptedException {
		Thread.sleep(2000);
		(submissionsButton).click();
		return this;
	}
}
