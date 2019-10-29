package com.canvas.qa.pages.apps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

public class AppDetailPage extends BasePage

{
	@FindBy(linkText = "Apps")
	WebElement appsButton;

	WebDriver driver;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(linkText = "Profile")
	WebElement myProfile;

	@FindBy(xpath = "//a[@title='View All Submissions']")
	WebElement submissionCount;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public AppDetailPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public AppDetailPage appsButtonClick() throws InterruptedException {
		Thread.sleep(2000);
		(appsButton).click();
		return this;
	}

	public String getSubmissionCount() {
		return submissionCount.getText().split(":")[1].trim();
	}

	public void logout() throws InterruptedException {
		Thread.sleep(5000);
		logout.click();
	}
}
