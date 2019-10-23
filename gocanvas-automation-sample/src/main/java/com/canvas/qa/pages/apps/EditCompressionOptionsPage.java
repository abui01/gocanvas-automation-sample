package com.canvas.qa.pages.apps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.CreateAppPage;

/**
 * @author shalini.pathak
 *
 */
public class EditCompressionOptionsPage extends BasePage {

	String appNameLinkXpath = "//a[text() = '%s']";

	@FindBy(xpath = "//a[text() = 'Apps']")
	WebElement appsLink;

	@FindBy(xpath = "//span[contains(.,'Cancel')]")
	WebElement cancelButton;

	@FindBy(id = "compression_dimension")
	WebElement compressionDimension;

	@FindBy(id = "compression_quality")
	WebElement compressionQuality;

	WebDriver driver;

	@FindBy(id = "compression")
	WebElement imageQualityDDL;

	@FindBy(xpath = "//span[contains(.,'Save')]")
	WebElement saveButton;

	public EditCompressionOptionsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public CreateAppPage clickAppLink() {
		appsLink.click();
		return new CreateAppPage(driver);
	}

	public EditAppPage clickAppNameLink(String appName) {
		driver.findElement(By.xpath(String.format(appNameLinkXpath, appName))).click();
		return new EditAppPage(driver);
	}

	public EditAppPage clickCancelButton() {
		clickOnHiddenElement(cancelButton, driver);
		return new EditAppPage(driver);
	}

	public EditAppPage clickSaveButton() {
		clickOnHiddenElement(saveButton, driver);
		return new EditAppPage(driver);
	}

	public String getCompressionDimension() {
		return compressionDimension.getAttribute("value");
	}

	public String getCompressionQuality() {
		return compressionQuality.getAttribute("value");
	}

	public String getImageQualityValue() {
		Select dropdown = new Select(imageQualityDDL);
		return dropdown.getFirstSelectedOption().getText();
	}

	public EditCompressionOptionsPage selectImageQuality(String value) {
		Select dropdown = new Select(imageQualityDDL);
		dropdown.selectByVisibleText(value);
		return this;
	}
}
