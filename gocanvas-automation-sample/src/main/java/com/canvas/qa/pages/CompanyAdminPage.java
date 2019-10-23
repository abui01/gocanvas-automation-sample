package com.canvas.qa.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CompanyAdminPage extends BasePage {

	@FindBy(linkText = "Apps")
	WebElement Apps;

	@FindBy(xpath = "//h1[contains(.,'Apps')]")
	WebElement Apps1;
	@FindBy(linkText = "Dashboard")
	WebElement dashboardlink;

	@FindBy(xpath = "//h1[contains(.,'Dashboard')]")
	WebElement dashboardlink1;
	WebDriver driver;

	@FindBy(xpath = "//ul[@id = 'side-menu']/li[@class != 'nav-header' and @class != 'side_bar_app_store']")
	WebElement elements;
	@FindBy(linkText = "Profile")
	WebElement Profile;

	@FindBy(xpath = "//h1[contains(.,'Profile')]")
	WebElement Profile1;
	@FindBy(linkText = "Reference Data & Images")
	WebElement ReferenceDataAndImages;

	@FindBy(xpath = "//h1[contains(.,'Reference Data & Images')]")
	WebElement ReferenceDataAndImages1;
	@FindBy(linkText = "Submissions")
	WebElement Submissions;
	@FindBy(xpath = "//h1[contains(.,'Submissions')]")
	WebElement Submissions1;

	public CompanyAdminPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public String checkForAppsLink() {
		String successText;
		try {
			Apps.click();
			successText = Apps1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForDashboardLink() {
		String successText;
		try {
			dashboardlink.click();
			successText = dashboardlink1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForProfileLink() {
		String successText;
		try {
			Profile.click();
			successText = Profile1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForReferenceDataAndImageLink() {
		String successText;
		try {
			ReferenceDataAndImages.click();
			successText = ReferenceDataAndImages1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForSubmissionsLink() {
		String successText;
		try {
			Submissions.click();
			successText = Submissions1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public boolean PremissonOnLinks(String[] links) throws IOException, InterruptedException

	{
		// (Fields1).click();

		boolean flag = false;
		driver.findElement(By.xpath("//span[contains(.,'Account')]")).click();
		Thread.sleep(4000);
		List<WebElement> allLinks = driver.findElements(
				By.xpath("//ul[@id = 'side-menu']/li[@class != 'nav-header' and @class != 'side_bar_app_store']"));

		Thread.sleep(4000);

		for (String link : links) {
			flag = false;
			for (WebElement p : allLinks) {
				if (link.equals(p.getText())) {
					flag = true;
					break;
				}
			}
			if (flag = false)
				break;
		}

		return flag;
	}
}
