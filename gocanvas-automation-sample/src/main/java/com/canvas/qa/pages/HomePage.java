package com.canvas.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.profile.CreateTrialAccountPage;
import com.canvas.qa.pages.profile.OnboardingPage;

public class HomePage extends BasePage {

	private WebDriver driver;

	@FindBy(linkText = "Try It Free")
	WebElement tryCanvasFree;
	
	String inputFieldXpath = "//input[@id = '%s']";
	@FindBy(id = "step1-submit")
	WebElement tryItFreeButton;
	@FindBy(xpath = "//h1[contains(.,'Just one more step to start your free')]")
	WebElement headermsg;
	
	
	@FindBy(id = "two-step-submit-btn")
	WebElement getStarted;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public CreateTrialAccountPage tryItFreeButtonClick() {
		clickOnHiddenElement(tryCanvasFree, driver);
		return new CreateTrialAccountPage(driver);
	}

	public void enterFieldData(String fieldID[], String data[],String email) {
		
		for (int i = 0; i < fieldID.length; i++) {
			if (i == 2) {
				WebElement element = makeXpath(inputFieldXpath, fieldID[i]);
				fluentWait(element, driver).sendKeys(email);
			} else {
				WebElement element = makeXpath(inputFieldXpath, fieldID[i]);
				fluentWait(element, driver).sendKeys(data[i]);
			}
		}
	}
	
	public HomePage clickTryItFreeButton() {
		clickOnHiddenElement(fluentWait(tryItFreeButton, driver), driver);
		return new HomePage(driver);
	}
}
