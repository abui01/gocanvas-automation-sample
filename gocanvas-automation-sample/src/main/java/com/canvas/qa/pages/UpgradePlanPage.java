package com.canvas.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UpgradePlanPage extends BasePage {

	@FindBy(xpath = "//span[contains(., 'Confirm')]")
	WebElement confirmButton;

	WebDriver driver;

	public UpgradePlanPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public DashboardPage clickConfirmButton() {
		clickOnHiddenElement(fluentWait(confirmButton, driver), driver);
		return new DashboardPage(driver);
	}
}