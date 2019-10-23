package com.canvas.qa.pages.apps;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.CreateAppPage;

public class TemplatesPage extends BasePage {

	String actionItemDisabledXpath = "//td[contains(.,'%s')]//following-sibling::td[@class = 'action_control']//a[@data-original-title = '%s']//i[contains(.,'text-muted')]";

	String actionItemXpath = "//td[contains(.,'%s')]//following-sibling::td[@class = 'action_control']//a[@data-original-title = '%s']";

	@FindBy(xpath = "//a[text() = 'Apps']")
	WebElement appsLink;

	WebDriver driver;

	String templateXpath = "//td[contains(.,'%s')]";

	public TemplatesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public CreateAppPage clickActionItemCreateApp(String templateName, String actionName) {
		WebElement element = driver.findElement(By.xpath(String.format(actionItemXpath, templateName, actionName)));
		clickOnHiddenElement(element, driver);
		//element.click();
		return new CreateAppPage(driver);
	}

	public TemplatesPage clickActionItemDelete(String templateName, String actionName) {
		WebElement element = driver.findElement(By.xpath(String.format(actionItemXpath, templateName, actionName)));
		element.click();
		driver.switchTo().alert().accept();
		return this;
	}

	public AppBuilderPage clickActionItemEdit(String templateName, String actionName) {
		WebElement element = driver.findElement(By.xpath(String.format(actionItemXpath, templateName, actionName)));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		//element.click();
		return new AppBuilderPage(driver);
	}

	public ShareTemplatePage clickActionItemShareForAuthorized(String templateName, String actionName) {
		WebElement element = driver.findElement(By.xpath(String.format(actionItemXpath, templateName, actionName)));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		//element.click();
		return new ShareTemplatePage(driver);
	}

	public CreateAppPage clickActionItemShareForNotAuthorized(String templateName, String actionName) {
		WebElement element = driver.findElement(By.xpath(String.format(actionItemXpath, templateName, actionName)));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		//element.click();
		return new CreateAppPage(driver);
	}

	public CreateAppPage clickAppLink() {
		appsLink.click();
		return new CreateAppPage(driver);
	}

	public boolean isActionItemDisplayed(String templateName, String actionName) {
		WebElement element = driver.findElement(By.xpath(String.format(actionItemXpath, templateName, actionName)));
		return element.isDisplayed();
	}

	public boolean isActionItemDisplayedDisabled(String templateName, String actionName) {
		WebElement element = driver.findElement(By.xpath(String.format(actionItemXpath, templateName, actionName)));
		return element.isDisplayed();
	}

	public boolean isTemplateDisplayed(String templateName) {
		List<WebElement> elements = driver.findElements(By.xpath(String.format(templateXpath, templateName)));
		return (elements.size() > 0);
	}
}
