package com.canvas.qa.pages.apps;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.profile.LoginPage;

public class ShowIntegrationOptionsPage extends BasePage {

	String appNameLinkXpath = "//a[text() = '%s']";

	@FindBy(xpath = "//a[contains(.,'Authorize')]")
	WebElement authorizeButton;

	@FindBy(xpath = "//div[@class = 'ibox ibox-content text-center border-connected']")
	WebElement connected;

	String disabledIntegrateButtonXpath = "//img[contains(@alt,'%s')]//parent::div//following-sibling::span[text() = 'Activate' and (contains(@class,'disabled'))]";
	
	@FindBy(xpath = "(//span[@class='btn btn-grey btn-w-s disabled'])[1]")
	WebElement disabledIntegrateButton;

	String disconnectButtonXpath = "//a[contains(@href,'%s') and (text() = 'Disconnect')]";
	
	@FindBy(xpath = "//a[@class='center-block text-center']")
	WebElement disconnectButton;
	
	@FindBy(xpath = "(//a[@class='btn btn-primary btn-w-s m-b-sm'])[1]")
	WebElement integrateButton;

	public WebDriver driver;

	String integrateButtonXpath = "//a[contains(@href,'%s')]//span[text() = 'Activate']";

	@FindBy(xpath = "//span[contains(., 'Log Out')]")
	WebElement logOutButton;

	@FindBy(xpath = "//a[contains(.,'Manage')]")
	WebElement manageButton;

	String manageButtonXpath = "//a[contains(@href,'%s') and (text() = 'Manage')]";

	String optionsXpath = "//img[@alt = '%s']";

	@FindBy(id = "storage_integration_submit")
	WebElement saveButton;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toastMessage;

	public ShowIntegrationOptionsPage(WebDriver driver) {
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
	
	public ShowIntegrationOptionsPage clickDisconnectButton(String optionName) {
		WebElement element = driver.findElement(By.xpath(String.format(disconnectButtonXpath, optionName)));
		element.click();
		driver.switchTo().alert().accept();
		return this;
	}

	public ShowIntegrationOptionsPage clickIntegrateButton(String optionName) {
//		WebElement element = driver.findElement(By.xpath(String.format(integrateButtonXpath, optionName)));
//		element.click();
		integrateButton.click();
		return this;
	}
	public LoginPage clickLogOutButton() {
		clickOnHiddenElement(logOutButton, driver);
		return new LoginPage(driver);
	}

	public ShowIntegrationOptionsPage clickSaveButton() {
		saveButton.click();
		return this;
	}

	public String getToastMessage() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(toastMessage));
		return toastMessage.getText();
	}

	public boolean isConnectedDisplayed() {
		return connected.isDisplayed();
	}

	public boolean isDisconnectButtonDisplayed() {
//		WebElement element = driver.findElement(By.xpath(String.format(disconnectButtonXpath, optionName)));
//		return element.isDisplayed();
		return disconnectButton.isDisplayed();
	}
	
	public ShowIntegrationOptionsPage disconnectButtonCheck() {
		
		List<WebElement> checkDisconnectButtonExists = driver.findElements(By.xpath("//a[@class='center-block text-center']"));
		int buttonSize = checkDisconnectButtonExists.size();
		
		if (buttonSize > 0)
		{
			disconnectButton.click();
			driver.switchTo().alert().accept();
		}
		else {
			//do nothing
		}
		return this;
	}

	public boolean isIntegrateButtonDisabled(String optionName) {
//		WebElement element = driver.findElement(By.xpath(String.format(disabledIntegrateButtonXpath, optionName)));
//		return element.isDisplayed();
		return disabledIntegrateButton.isDisplayed();
	}

	public boolean isIntegrationButtonDisplayed(String optionName) {
//		WebElement element = driver.findElement(By.xpath(String.format(integrateButtonXpath, optionName)));
//		return element.isDisplayed();
		return integrateButton.isDisplayed();
	}

	public boolean isManageButtonDisplayed() {
		return manageButton.isDisplayed();
	}

	public boolean isManageButtonDisplayed(String optionName) {
		WebElement element = driver.findElement(By.xpath(String.format(manageButtonXpath, optionName)));
		return element.isDisplayed();
	}

	public boolean isOptionDisplayed(String optionName) {
		WebElement element = driver.findElement(By.xpath(String.format(optionsXpath, optionName)));
		return element.isDisplayed();
	}
}
