package com.canvas.qa.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CompanyWeekly2of2Page extends BasePage {

	@FindBy(xpath = "//h1[contains(.,'Add Users')]")
	WebElement addsuerheader;
	@FindBy(xpath = "//a[contains(.,'Add Users')]")
	WebElement adduser;
	// @FindBy(xpath = "//a[contains(.,'Customize') And @class ='btn btn-primary
	// btn-w-s pull-right']")
	@FindBy(xpath = "//a[contains(@href,'dash=true')]")
	WebElement customizebutton;
	// @FindBy(linkText = "Dashboard")
	@FindBy(xpath = "//nav/ul/li/a[contains(., 'Dashboard')]")
	WebElement dashboardlink;
	WebDriver driver;
	@FindBy(xpath = "//h1[contains(.,'Apps')]")
	WebElement manageappheader;

	// @FindBy(xpath = "//a[contains(.,'Manage Apps')]")
	@FindBy(xpath = "//nav/ul/li/a[contains(., 'Apps')]")
	WebElement manageApps;
	@FindBy(xpath = "//nav/ul/li/a[contains(., 'Account')]")
	WebElement profilelink;

	@FindBy(xpath = "//h1[contains(.,'Return On Investment')]")
	WebElement roimsg;

	@FindBy(xpath = "//h1[contains(.,'Submissions')]")
	WebElement submissionlink;

	@FindBy(xpath = "//a[contains(.,'Users')]")
	WebElement users;

	@FindBy(xpath = ".//*[@class ='btn btn-primary m-t-sm pull-right']")
	WebElement viewall;

	public CompanyWeekly2of2Page(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public String checkForAddUsersScreen() {
		String successText;
		try {
			profilelink.click();
			users.click();
			adduser.click();
			//Thread.sleep(4000);
			// dashboardlink.click();
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// js.executeScript("arguments[0].click();", adduser);
			successText = fluentWait(addsuerheader, driver).getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForAppsscreen() {
		String successText;
		try {
			dashboardlink.click();
			manageApps.click();
			// Thread.sleep(2000);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// js.executeScript("arguments[0].click();", manageApps);
			successText = manageappheader.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}
		return successText;
	}

	public String checkForReturnonInvestmentscreen() {
		String successText;
		try {
			dashboardlink.click();
			// Thread.sleep(2000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", customizebutton);
			//Thread.sleep(3000);
			successText = fluentWait(roimsg, driver).getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}
		return successText;
	}

	public String checkForSubmissionscreen() {

		String successText;
		try {
			viewall.click();
			// Thread.sleep(2000);
			successText = submissionlink.getText();
			// Thread.sleep(2000);
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

}
