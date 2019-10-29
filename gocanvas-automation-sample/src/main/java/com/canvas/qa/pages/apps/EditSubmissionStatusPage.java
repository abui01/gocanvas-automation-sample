package com.canvas.qa.pages.apps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

public class EditSubmissionStatusPage extends BasePage {

	String appNameLinkXpath = "//a[text() = '%s']";

	WebDriver driver;

	@FindBy(xpath = "//tr[@id = 'status_0']/td[1]")
	WebElement satusText;

	public EditSubmissionStatusPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public EditAppPage clickAppNameLink(String appName) {
		driver.findElement(By.xpath(String.format(appNameLinkXpath, appName))).click();
		return new EditAppPage(driver);
	}

	public String getStatus() {
		return satusText.getText();
	}
}
