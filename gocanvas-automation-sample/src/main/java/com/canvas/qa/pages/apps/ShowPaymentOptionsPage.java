package com.canvas.qa.pages.apps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.profile.LoginPage;

public class ShowPaymentOptionsPage extends BasePage {

	String appNameLinkXpath = "//a[text() = '%s']";

	@FindBy(id = "authorize_link")
	WebElement authorizeButton;

	@FindBy(xpath = "//a[contains(.,'Disconnect')]")
	// @FindBy(xpath = "//strong[contains(.,'Integration Options:')]")

	WebElement disconnectButton;

	WebDriver driver;

	@FindBy(xpath = "//span[contains(.,'Integrate')]")
	WebElement integrateButton;

	@FindBy(xpath = "//div[@id = 'integration_status']/p")
	WebElement integrationStatus;

	@FindBy(xpath = "//span[contains(., 'Log Out')]")
	WebElement logOutButton;

	@FindBy(id = "payment_integration_submit")
	WebElement saveButton;

	public ShowPaymentOptionsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public EditAppPage clickAppNameLink(String appName) {
		driver.findElement(By.xpath(String.format(appNameLinkXpath, appName))).click();
		return new EditAppPage(driver);
	}

	public void clickAuthorizeButton() {
		authorizeButton.click();
	}

	public ShowPaymentOptionsPage clickDisconnectButtonAuthorized(String message) {
		disconnectButton.click();
		driver.switchTo().alert().accept();
		return this;
	}

	public CreateAppPage clickDisconnectButtonNotAuthorized(String message, String stepNumber,
			ITestContext testContext) {
		disconnectButton.click();
		boolean status = driver.switchTo().alert().getText().contains(message);
		reportLog(status, testContext.getName(), "Verify we are prompted 'Delete this integration?", stepNumber,
				"Pop up message appears: Delete this integration?");
		org.testng.Assert.assertTrue(status);

		driver.switchTo().alert().accept();
		return new CreateAppPage(driver);
	}

	public ShowPaymentOptionsPage clickIntegrateButton() {
		integrateButton.click();
		return this;
	}

	public LoginPage clickLogOutButton() {
		clickOnHiddenElement(logOutButton, driver);
		return new LoginPage(driver);
	}

	public ShowPaymentOptionsPage clickSaveButton() {
		saveButton.click();
		return this;
	}

	public String getIntegrationStatus() {
		return integrationStatus.getText();
	}

	public boolean isDisconnectDisplayed() {
		return disconnectButton.isDisplayed();
	}

	public boolean isIntegrateDisplayed() {
		return integrateButton.isDisplayed();
	}
}
