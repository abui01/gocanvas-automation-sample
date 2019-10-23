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
public class EditTableOfContentsPage extends BasePage {

	String appNameLinkXpath = "//a[text() = '%s']";

	WebDriver driver;

	@FindBy(id = "btn_Save")
	WebElement saveButton;

	@FindBy(xpath = "//strong[contains(.,'Table of Contents:')]//../following-sibling::div//input[@type='checkbox']")
	WebElement tableOfContentsCheckbox;

	@FindBy(xpath = "//strong[contains(.,'Table of Contents:')]//../following-sibling::div//div[@class='slider round']")
	WebElement tableOfContentsEnabledCheckbox;

	@FindBy(className = "toast-message")
	WebElement toast;

	@FindBy(xpath = "//strong[contains(.,'Table of Contents')]")
	WebElement tocSetting;

	public EditTableOfContentsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public EditAppPage clickAppNameLink(String appName) {
		driver.findElement(By.xpath(String.format(appNameLinkXpath, appName))).click();
		return new EditAppPage(driver);
	}

	public EditAppPage clickSaveButton() {
		saveButton.click();
		return new EditAppPage(driver);
	}

	public boolean clickTableOfContentsCheckBox(String toastEnable) throws InterruptedException {
		fluentWait(tableOfContentsEnabledCheckbox, driver).click();
		boolean toastEnableMsg = toast.getText().equals(toastEnable);
		return toastEnableMsg;
	}

	public boolean isTableOfContentsEnabledChecked() {
		return tableOfContentsCheckbox.isSelected();
	}
}
