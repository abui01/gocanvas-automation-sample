package com.canvas.qa.pages.apps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

/**
 * @author shalini.pathak
 *
 */
public class EditDispatchEnabledPage extends BasePage {

	String appNameLinkXpath = "//a[text() = '%s']";

	@FindBy(xpath = "//input[@id = 'form_dispatch_enabled' and @type = 'checkbox']")
	WebElement dispatchEnabledCheckbox;

	WebDriver driver;

	public EditDispatchEnabledPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public EditAppPage clickAppNameLink(String appName) {
		driver.findElement(By.xpath(String.format(appNameLinkXpath, appName))).click();
		return new EditAppPage(driver);
	}

	public boolean isDispatchEnabledChecked() {
		return dispatchEnabledCheckbox.isSelected();
	}
}
