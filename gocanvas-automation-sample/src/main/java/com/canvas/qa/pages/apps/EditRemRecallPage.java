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
public class EditRemRecallPage extends BasePage {

	String appNameLinkXpath = "//a[text() = '%s']";

	WebDriver driver;

	// @FindBy(xpath = "//input[@id = 'form_remrecall_enabled' and @type =
	// 'checkbox']")
	@FindBy(xpath = "//strong[contains(.,'Remember & Recall:')]//../following-sibling::div//input[@type='checkbox']")
	WebElement remRecallEnabledCheckbox;

	public EditRemRecallPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public EditAppPage clickAppNameLink(String appName) {
		driver.findElement(By.xpath(String.format(appNameLinkXpath, appName))).click();
		return new EditAppPage(driver);
	}

	public boolean isRemRecallEnabledChecked() {
		return remRecallEnabledCheckbox.isSelected();
	}
}
