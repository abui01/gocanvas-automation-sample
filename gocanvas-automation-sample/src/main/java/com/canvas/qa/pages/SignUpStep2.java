package com.canvas.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpStep2 extends BasePage {

	private WebDriver driver;
	@FindBy(id = "two-step-submit-btn")
	WebElement getStarted;
	@FindBy(xpath = "//h1[contains(.,'Just one more step to start your free')]")
	WebElement headermsg_1;
	@FindBy(xpath = "//h3[contains(.,'Welcome to GoCanvas.')]")
	WebElement headermsg_2;

	String inputFieldXpath = "//input[@id = '%s']";


	public SignUpStep2(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// mixedContentChecker(driver);
		javaScriptErrorChecker(driver);
	}
	public SignUpStep2 clickGetStartedButton() {
		fluentWait(getStarted, driver).click();
	
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(headermsg_2));
		return new SignUpStep2(driver);
	}
	public SignUpStep2 enterFieldData(String fieldID[], String data[]) {
		for (int i = 0; i < fieldID.length; i++) {
			addDataInFields(inputFieldXpath,fieldID[i], data[i]);
		}
		return new SignUpStep2(driver);
	}

	public boolean verifyMessage(String message) {
		boolean headerText = headermsg_1.getText().equals(message);
		return headerText;
	}

	public boolean verifyMessageInThankYouPage(String message) {
		boolean headerText = headermsg_2.getText().equals(message);
		return headerText;
	}

}
