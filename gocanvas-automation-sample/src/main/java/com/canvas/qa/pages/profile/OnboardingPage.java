package com.canvas.qa.pages.profile;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

public class OnboardingPage extends BasePage {
	String buttonXpath = "//a[contains(.,'%s')]";

	WebDriver driver;

	@FindAll({ @FindBy(xpath = "//a[contains(.,'Let me explore on my own')]"),
			@FindBy(linkText = "Let me explore on my own") })
	WebElement exploreButton;

	@FindBy(className = "branding")
	WebElement gocanvasButton;

	@FindBy(xpath = "//h1")
	WebElement header;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement message;

	String messageXpath = "//*[contains(.,'%s')]";

	@FindAll({ @FindBy(xpath = "//a[contains(.,'Resend Email')]"), @FindBy(linkText = "Resend Email") })
	WebElement resendButton;

	@FindBy(linkText = "Skip")
	WebElement skip;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public OnboardingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

		javaScriptErrorChecker(driver);
	}

	public OnboardingPage clickButton(String button) {
		WebElement element = driver.findElement(By.xpath(String.format(buttonXpath, button)));
		clickOnHiddenElement(element, driver);
		customWait(2);
		return this;
	}

	public OnboardingPage clickSkip() {
		(skip).click();
		return this;
	}

	public OnboardingPage exploreButtonClick() {
		customWait(15);
		clickOnHiddenElement(exploreButton, driver);
		/**
		 * (exploreButton).click();
		 */
		return this;
	}

	public String getHeader() {
		return (header).getText();
	}

	public String getMessage() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(message));
		return (message).getText();
	}

	public OnboardingPage gocanvasButtonClick() {
		(gocanvasButton).click();
		return this;
	}

	public boolean isButtonDisplayed(String button) {
		WebElement element = driver.findElement(By.xpath(String.format(buttonXpath, button)));
		return element.isDisplayed();
	}

	public boolean isMessageDisplayed(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(messageXpath, text)));
		return element.isDisplayed();
	}

	public OnboardingPage resendActivationClick() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", resendButton);
		/** 
		 * (resendButton).click();
		 */
		return this;
	}
}
