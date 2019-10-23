package com.canvas.qa.pages.apps;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

/**
 * @author taukeer.ahmad
 *
 */
public class CreateAppCommonPage extends BasePage

{

	@FindBy(className = "fa-plus")
	WebElement btnAddScreen;

	@FindBy(xpath = "//nav/ul/li/a[contains(., 'Apps')]")
	// @FindBy(linkText = "Apps")
	WebElement btnApps;
	@FindBy(xpath = "//button[contains(., 'Publish')]")
	WebElement btnFinalPublish;
	@FindBy(className = "multiline-text-icon")
	WebElement btnLongText;
	@FindBy(className = "modal-footer")
	WebElement btnModal;
	
	@FindBy(linkText = "Create App")
//	@FindBy(xpath = "//span[contains(.,'Create App')]")
//	@FindBy(xpath = "(//a[@class='btn-custom-big btn btn-primary top-margin-25 hidden-xs hidden-sm'])//span") 
	WebElement btnNewApp;
	
	@FindBy(className = "fa-tablet")
	WebElement btnPublish;
	@FindBy(className = "letsgo")
	WebElement btnStart;
	WebDriver driver;
	@FindBy(className = "single-line-text")
	WebElement fieldLabel;
	@FindBy(xpath = "//button[contains(., 'Next')]")
	WebElement form;
	@FindBy(linkText = "Log Out")
	WebElement logout;
	@FindBy(className = "screen-name")
	WebElement screenName;

	@FindBy(className = "table-striped")
	WebElement tblUsers;

	public CreateAppCommonPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public void createApp() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(btnApps));
		btnApps.click();
		fluentWait(btnNewApp, driver).click();
		btnStart.click();
		btnAddScreen.click();
		// screenName.sendKeys("Screen Name");
		btnLongText.click();

		// fieldLabel.sendKeys("Long Text Field");
		wait.until(ExpectedConditions.elementToBeClickable(btnPublish));
		btnPublish.click();
		customWait(5); //adding in wait for server load times
		WebElement btnConfirmPublish = btnModal.findElement(By.className("cvs-prim-btn"));
		wait.until(ExpectedConditions.elementToBeClickable(btnConfirmPublish));
		btnConfirmPublish.click();
		customWait(5); //adding in wait for server load times
		wait.until(ExpectedConditions.elementToBeClickable(form));
		clickOnHiddenElement(fluentWait(form, driver), driver);
		customWait(3); //adding in wait for server load times
		List<WebElement> users = driver
				.findElements(By.xpath("//tr[@ng-repeat = 'option in options']//td[2]"));
		for (WebElement u : users) {
	
			 
			fluentWait(u, driver).click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(btnFinalPublish));
		btnFinalPublish.click();

	}

	public void logout() throws InterruptedException {
		//Thread.sleep(5000);
		fluentWait(logout, driver).click();
	}
}