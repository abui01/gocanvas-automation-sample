package com.canvas.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FolderPage extends BasePage

{
	@FindBy(xpath = "//div[@class = 'table-odd folder ng-scope']//span[text() = 'Apps']")
	WebElement apps;

	@FindBy(xpath = "//a[text() = 'Apps']")
	WebElement appsLink;

	WebDriver driver;

	@FindBy(xpath = "//button[text() = 'Move']")
	WebElement moveButton;

	@FindBy(xpath = "//h4[contains(.,'Move Selected App')]")
	WebElement moveSelectedApp;

	String quickLinkMenuXpath = "//a[text() = '%s']/parent::td[1]/following-sibling::td[3]/a[@data-original-title = 'Quick Links']/parent::td/ul/li/a[text() = '%s']";

	String quickLinkXpath = "//a[text() = '%s']/parent::td[1]/following-sibling::td[3]/a[@data-original-title = 'Quick Links']/i";

	@FindBy(id = "common_search_field")
	List<WebElement> searchField;

	@FindBy(xpath = "//i[contains(@class,'fa fa-search text-muted')]")
	WebElement searchIcon;

	public FolderPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public CreateAppPage clickAppLink() {
		clickOnHiddenElement(appsLink, driver);
		return new CreateAppPage(driver);
	}

	public FolderPage clickApps() {
		clickOnHiddenElement(apps, driver);
		return this;
	}

	public FolderPage clickMoveButton() {
		moveButton.click();
		customWait(10);
		return this;
	}

	public FolderPage clickQuickLink(String appName) throws InterruptedException {
		if (searchField.size() > 0) {
			searchField.get(0).clear();
			searchField.get(0).sendKeys(appName);
			searchIcon.click();
			Thread.sleep(5000);
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(String.format(quickLinkXpath, appName)));
		js.executeScript("arguments[0].click();", element);
		return this;
	}

	public FolderPage clickQuickLinkMenu(String appName, String quickLinkMenu) throws InterruptedException {
		WebElement element = driver.findElement(By.xpath(String.format(quickLinkMenuXpath, appName, quickLinkMenu)));
		element.click();
		customWait(10);
		return this;
	}

	public boolean isMoveSelectedAppDisplayed() {
		return moveSelectedApp.isDisplayed();
	}

	public boolean isQuickLinkMenuDisplayed(String appName, String quickLinkMenu) {
		WebElement element = driver.findElement(By.xpath(String.format(quickLinkMenuXpath, appName, quickLinkMenu)));
		return element.isDisplayed();
	}
}
